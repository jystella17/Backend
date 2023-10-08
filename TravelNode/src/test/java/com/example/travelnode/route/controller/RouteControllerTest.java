package com.example.travelnode.route.controller;

import com.example.travelnode.controller.RouteController;
import com.example.travelnode.dto.RouteCreateRequestDto;
import com.example.travelnode.dto.RouteResponseDto;
import com.example.travelnode.entity.Keywords;
import com.example.travelnode.entity.ProviderType;
import com.example.travelnode.entity.RoleType;
import com.example.travelnode.oauth2.config.SecurityConfig;
import com.example.travelnode.oauth2.entity.UserPrincipal;
import com.example.travelnode.route.WithMockCustomOAuth2Account;
import com.example.travelnode.service.RouteService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.*;
import org.springframework.context.annotation.*;
import org.springframework.http.MediaType;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.*;

import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = RouteController.class, excludeFilters = {
        @ComponentScan.Filter(
                type = FilterType.ASSIGNABLE_TYPE,
                classes = SecurityConfig.class
        )
})
public class RouteControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    RouteService routeService;

    @Test
    @WithMockCustomOAuth2Account(role = RoleType.USER, provider = ProviderType.KAKAO)
    @DisplayName("새로운 루트 생성")
    public void registerRoute() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        List<Long> keywords = new ArrayList<>();
        keywords.add(1L); keywords.add(2L);
        RouteCreateRequestDto requestDto = new RouteCreateRequestDto(
                1L, keywords, "졸업 기념 서울여행", false, LocalDate.parse("2023-09-25")
        );

        List<Keywords> keywordsList = new ArrayList<>();
        keywordsList.add(new Keywords(1L, "힐링여행"));
        keywordsList.add(new Keywords(2L, "추억여행"));
        RouteResponseDto responseDto = new RouteResponseDto(1L, 1L, keywordsList,
                "졸업 기념 서울여행", LocalDate.parse("2023-09-25"), true);

        DefaultOAuth2User user = (DefaultOAuth2User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserPrincipal userPrincipal = new UserPrincipal(user.getName(), user.getAttributes().get("email").toString(),
                user.getAttributes().get("nickname").toString(), RoleType.USER, ProviderType.KAKAO,
                (Collection<GrantedAuthority>) user.getAuthorities());

        given(routeService.createRoute(requestDto, userPrincipal)).willReturn(responseDto);

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
        List<Keywords> keywords = new ArrayList<>();
        keywords.add(new Keywords(1L, "힐링여행"));
        keywords.add(new Keywords(2L, "추억여행"));
        RouteResponseDto responseDto = new RouteResponseDto(1L, 1L,keywords,
                "졸업 기념 서울여행", LocalDate.parse("2023-09-25"), true);

        given(routeService.searchByRouteId(1L)).willReturn(responseDto);

        mockMvc.perform(get("/api/v1/route/{routeId}", 1L)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithMockCustomOAuth2Account(role = RoleType.USER, provider = ProviderType.KAKAO)
    @DisplayName("루트 도시 변경")
    public void updateCity() throws Exception {
        given(routeService.updateCity(1L, 2L)).willReturn("부산");

        mockMvc.perform(patch("/api/v1/route/update-city/{routeId}", 1L)
                        .param("cityId", String.valueOf(2L))
                        .contentType(MediaType.APPLICATION_JSON).characterEncoding("UTF-8")
                        .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithMockCustomOAuth2Account(role = RoleType.USER, provider = ProviderType.KAKAO)
    @DisplayName("루트 키워드 변경")
    public void updateKeyword() throws Exception {
        Keywords keywords = new Keywords(3L, "맛집탐험");
        given(routeService.updateKeyword(1L, 2L, 3L)).willReturn(keywords);

        mockMvc.perform(patch("/api/v1/route/update-keyword/{routeId}", 1L)
                        .param("currentKey", String.valueOf(2L))
                        .param("newKey", String.valueOf(3L))
                        .contentType(MediaType.APPLICATION_JSON).characterEncoding("UTF-8")
                        .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithMockCustomOAuth2Account(role = RoleType.USER, provider = ProviderType.KAKAO)
    @DisplayName("루트 이름 변경")
    public void updateRouteName() throws Exception {
        given(routeService.updateRouteName(1L, "졸업여행")).willReturn("졸업여행");

        mockMvc.perform(patch("/api/v1/route/update-name/{routeId}", 1L)
                        .param("routeName", "졸업여행")
                        .contentType(MediaType.APPLICATION_JSON).characterEncoding("UTF-8")
                        .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithMockCustomOAuth2Account(role = RoleType.USER, provider = ProviderType.KAKAO)
    @DisplayName("루트 날짜 변경")
    public void updateRouteDay() throws Exception {
        given(routeService.updateRouteDay(1L, LocalDate.parse("2023-10-08")))
                .willReturn(LocalDate.parse("2023-10-08"));

        mockMvc.perform(patch("/api/v1/route/update-date/{routeId}", 1L)
                        .param("routeDay", String.valueOf(LocalDate.parse("2023-10-08")))
                        .contentType(MediaType.APPLICATION_JSON).characterEncoding("UTF-8")
                        .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithMockCustomOAuth2Account(role = RoleType.USER, provider = ProviderType.KAKAO)
    @DisplayName("루트 공개여부 변경")
    public void updateRoutePrivate() throws Exception {
        given(routeService.updateRoutePrivate(1L, false))
                .willReturn(false);

        mockMvc.perform(patch("/api/v1/route/update-private/{routeId}", 1L)
                        .param("isPrivate", String.valueOf(false))
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andDo(print())
                .andExpect(status().isOk());
    }
}