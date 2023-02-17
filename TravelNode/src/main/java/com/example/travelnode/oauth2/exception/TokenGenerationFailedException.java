package com.example.travelnode.oauth2.exception;

public class TokenGenerationFailedException extends RuntimeException {
    public TokenGenerationFailedException() {
        super("Failed to generate token");
    }

    private TokenGenerationFailedException(String message) {
        super(message);
    }
}
