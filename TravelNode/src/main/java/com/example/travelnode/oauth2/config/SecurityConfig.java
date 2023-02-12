package com.example.travelnode.oauth2.config;

import com.example.travelnode.oauth2.exception.JWTAuthenticationEntryPoint;
import com.example.travelnode.oauth2.exception.RestAuthenticationEntryPoint;
import com.example.travelnode.oauth2.handler.JWTAccessDeniedHandler;
import com.example.travelnode.oauth2.handler.OAuth2AuthenticationFailureHandler;
import com.example.travelnode.oauth2.handler.OAuth2AuthenticationSuccessHandler;
import com.example.travelnode.service.CustomOAuth2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsUtils;

@Configuration(proxyBeanMethods = false)
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomOAuth2UserService customOAuth2UserService;
    private final OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler;
    private final OAuth2AuthenticationFailureHandler oAuth2AuthenticationFailureHandler;
    private final JWTAccessDeniedHandler jwtAccessDeniedHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{

        http.sessionManagement()
                // 인증에 JWT 토큰을 사용하므로 Session을 생성하지 않음
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .exceptionHandling()
                // 잘못된/만료된/지원되지 않는 JWT 토큰을 사용했을 때의 예외처리
                .authenticationEntryPoint(new JWTAuthenticationEntryPoint())
                // 사용자가 허가받지 않은 API에 접근한 경우
                .accessDeniedHandler(jwtAccessDeniedHandler).and()
                .authorizeRequests()  // 인증된 유저만 접근 가능
                // CORS Preflight Request에 대해서는 spring security를 적용하지 않음
                .requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
                .antMatchers("/", "/login/oauth2/**").permitAll()
                // 로그인 URL과 Home URL에는 모든 사용자가 접근 가능
                .antMatchers(HttpMethod.POST, "/api/**")
                .hasAnyRole("USER", "ADMIN")
                .antMatchers("/admin/**").hasRole("ADMIN")
                // Home과 로그인 URL을 제외한 페이지는 관리자 혹은 가입된 유저만 접근 가능
                .anyRequest().authenticated().and() // 이외에는 인증된 사용자만 접근 가능
                .oauth2Login() // oauth2Login 설정
                .authorizationEndpoint().baseUri("/login/oauth2").and()
                .redirectionEndpoint().baseUri("/*/oauth2/code/*").and()
                .userInfoEndpoint() // oauth2Login 성공 이후의 설정
                .userService(customOAuth2UserService).and()// 로그인 성공 이후의 task는 customOAuth2UserService에서 처리
                .successHandler(oAuth2AuthenticationSuccessHandler)
                .failureHandler(oAuth2AuthenticationFailureHandler).and()
                // JWT 토큰 인증을 위한 Filter 등록
                //.addFilterBefore(new JwtFilter(), UsernamePasswordAuthenticationFilter.class)
                .csrf().disable()
                .formLogin().disable()
                .httpBasic().disable()
                .logout().logoutSuccessUrl("/");

        return http.build();
    }
}
