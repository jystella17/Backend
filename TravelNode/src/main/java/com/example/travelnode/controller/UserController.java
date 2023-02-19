package com.example.travelnode.controller;

import com.example.travelnode.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
    @Autowired private UserRepository userRepository;

    @GetMapping("/oauth2/login-info")
    public String oauth2Login(Authentication authentication, @AuthenticationPrincipal OAuth2User oAuth2UserPrincipal) throws Exception {
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        Map<String, Object> attributes = oAuth2User.getAttributes();
        System.out.println("&" + attributes);

        Map<String, Object> userPrincipal = oAuth2UserPrincipal.getAttributes();
        if(attributes != userPrincipal) {
            throw new Exception("Error");
        }

        return attributes.toString();
    }
}
