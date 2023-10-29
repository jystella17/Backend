package com.example.travelnode.route.controller;

import com.example.travelnode.controller.RouteController;
import com.example.travelnode.dto.PlaceResponseDto;
import com.example.travelnode.entity.ProviderType;
import com.example.travelnode.entity.RoleType;
import com.example.travelnode.oauth2.config.SecurityConfig;
import com.example.travelnode.route.WithMockCustomOAuth2Account;
import com.example.travelnode.service.RoutePlaceService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
public class RoutePlaceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RoutePlaceService routePlaceService;

    @Test
    @DisplayName("루트에 포함된 장소 목록")
    public void placeListInRoute() throws Exception {
        List<PlaceResponseDto> places = new ArrayList<>();
        places.add(new PlaceResponseDto("경복궁", "서울특별시 종로구", 1L,
                "졸업 기념 서울여행", 1, LocalDateTime.parse("2023-10-25 10:03:37")));
        places.add(new PlaceResponseDto("여의도IFC몰", "서울특별시 영등포구 국제금융로", 1L,
                "졸업 기념 서울여행", 2, LocalDateTime.parse("2023-10-25 11:35:23")));
        places.add(new PlaceResponseDto("여의도한강공원", "서울특별시 영등포구 여의나루", 1L,
                "졸업 기념 서울여행", 3, LocalDateTime.parse("2023-10-25 13:07:47")));
        places.add(new PlaceResponseDto("가로수길", "서울특별시 강남구 신사동", 1L,
                "졸업 기념 서울여행", 4, LocalDateTime.parse("2023-10-25 15:35:37")));
        places.add(new PlaceResponseDto("신세계백화점강남", "서울특별시 서초구 신반포로", 1L,
                "졸업 기념 서울여행", 5, LocalDateTime.parse("2023-10-25 17:43:37")));

        given(routePlaceService.allPlacesInRoute(1L)).willReturn(places);

        mockMvc.perform(get("/api/v1/place/place-list")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithMockCustomOAuth2Account(role = RoleType.USER, provider = ProviderType.KAKAO)
    @DisplayName("루트에 장소 추가")
    public void registerPlace() throws Exception {

    }
}
