package com.example.travelnode.route.controller;

import com.example.travelnode.controller.RouteController;
import com.example.travelnode.dto.RouteCreateRequestDto;
import com.example.travelnode.dto.RouteResponseDto;
import com.example.travelnode.entity.ProviderType;
import com.example.travelnode.entity.RoleType;
import com.example.travelnode.entity.Route;
import com.example.travelnode.oauth2.config.SecurityConfig;
import com.example.travelnode.oauth2.entity.UserPrincipal;
import com.example.travelnode.repository.RouteRepository;
import com.example.travelnode.route.WithMockCustomOAuth2Account;
import com.example.travelnode.route.WithMockCustomOAuth2AccountSecurityContextFactory;
import com.example.travelnode.service.RouteService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.*;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.test.context.support.WithSecurityContext;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import javax.transaction.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import static org.mockito.BDDMockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = RouteController.class,
        excludeFilters = {
                @ComponentScan.Filter(
                        type = FilterType.ASSIGNABLE_TYPE,
                        classes = SecurityConfig.class
                )
        })
public class RouteControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    RouteService routeService;

    @MockBean
    RouteResponseDto routeResponseDto;

    @MockBean
    RouteRepository routeRepository;

    @Test
    @WithMockCustomOAuth2Account(id = "1234567", email = "stella@gmail.com",
            nickname = "Traveler", role = RoleType.USER, provider = ProviderType.KAKAO)
    @DisplayName("새로운 루트 생성")
    public void registerRoute() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        Long cityId = 1L, routeId = 1L;
        // List<Long> keywords = new ArrayList<>();
        // keywords.add(1L); keywords.add(2L);
        
        String routeName = "졸업 기념 서울여행";
        Boolean isPrivate = false;
        LocalDate routeDay = LocalDate.parse("2023-09-25");

        SecurityContext securityContext = SecurityContextHolder.getContext();
        DefaultOAuth2User oAuth2User = (DefaultOAuth2User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Map<String, Object> principal = oAuth2User.getAttributes();

        UserPrincipal userPrincipal = new UserPrincipal(principal.get("id").toString(), principal.get("email").toString(),
                principal.get("nickname").toString(), RoleType.USER, ProviderType.KAKAO,
                (Collection<GrantedAuthority>) securityContext.getAuthentication().getAuthorities());

        RouteCreateRequestDto requestDto = new RouteCreateRequestDto(
                cityId, routeName, isPrivate, routeDay
        );

        mockMvc.perform(post("/api/v1/route/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(objectMapper.writeValueAsBytes(requestDto))
                        .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithMockCustomOAuth2Account(role = RoleType.USER, provider = ProviderType.KAKAO)
    @DisplayName("루트 정보 조회")
    public void routeDetails() throws Exception {
        given(routeService.searchByRouteId(1L)).willReturn(routeResponseDto);

        mockMvc.perform(get("/api/v1/route/{routeId}", routeResponseDto.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andDo(print())
                .andExpect(status().isOk());
    }
}