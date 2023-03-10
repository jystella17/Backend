package com.example.travelnode.oauth2.exception;

public class TokenGenerationFailedException extends RuntimeException {
    public TokenGenerationFailedException() {
        super("Failed to generate token");
    }

    private TokenGenerationFailedException(String message) {
        super(message);
    }
<<<<<<< HEAD
}
=======
}
>>>>>>> dfbb0612c0f0a4bbb049e0ec2752641cd4a8cce8
