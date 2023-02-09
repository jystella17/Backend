package com.example.travelnode.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class OauthController {

    @GetMapping("/kakao")
    public void kakaoCallback(@RequestParam String code) throws Exception {
        System.out.println(code);
    }
}
