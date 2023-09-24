package com.example.travelnode.route.controller;

import com.example.travelnode.controller.RouteController;
import com.example.travelnode.dto.RouteResponseDto;
import com.example.travelnode.oauth2.service.CustomOAuth2UserService;
import com.example.travelnode.oauth2.service.CustomUserDetailsService;
import com.example.travelnode.service.RoutePlaceService;
import com.example.travelnode.service.RouteService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.MockBeans;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = RouteController.class)
@MockBeans({
        @MockBean(RouteService.class),
        @MockBean(RoutePlaceService.class),
        @MockBean(CustomOAuth2UserService.class),
        @MockBean(CustomUserDetailsService.class)
})
public class RouteControllerClass {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void routeDetails(Long routeId) throws Exception {
        mockMvc.perform(get("/api/v1/route/{routeId}"))
                .andDo(print())
                .andExpect(status().isOk());
    }
}
