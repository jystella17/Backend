package com.example.travelnode.oauth2.response;

import lombok.Getter;

@Getter
public class ApiResponseHeader {
    private final int code;
    private final String message;

    public ApiResponseHeader(int code, String message) {
        this.code = code;
        this.message = message;
    }
<<<<<<< HEAD
}
=======
}
>>>>>>> dfbb0612c0f0a4bbb049e0ec2752641cd4a8cce8
