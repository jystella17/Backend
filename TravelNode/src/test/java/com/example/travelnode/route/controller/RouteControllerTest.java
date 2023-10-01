package com.example.travelnode.route.controller;

import com.example.travelnode.controller.RouteController;
import com.example.travelnode.dto.RouteCreateRequestDto;
import com.example.travelnode.dto.RouteResponseDto;
import com.example.travelnode.entity.ProviderType;
import com.example.travelnode.entity.RoleType;
import com.example.travelnode.oauth2.config.SecurityConfig;
import com.example.travelnode.route.WithMockCustomOAuth2Account;
import com.example.travelnode.service.RouteService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.*;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.mockito.BDDMockito.*;
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

    @MockBean
    RouteService routeService;

    @MockBean
    RouteResponseDto routeResponseDto;

    @Test
    @WithMockCustomOAuth2Account(role = RoleType.USER, provider = ProviderType.KAKAO)
    @DisplayName("새로운 루트 생성")
    public void registerRoute() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        Long cityId = 1L;
        // List<Long> keywords = new ArrayList<>();
        // keywords.add(1L); keywords.add(2L);
        
        String routeName = "졸업 기념 서울여행";
        Boolean isPrivate = false;
        LocalDate routeDay = LocalDate.parse("2023-09-25");

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