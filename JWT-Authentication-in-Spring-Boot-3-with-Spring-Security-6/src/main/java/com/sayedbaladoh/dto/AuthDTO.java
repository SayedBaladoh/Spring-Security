package com.sayedbaladoh.dto;


public class AuthDTO {
    public record LoginRequest(String username, String password) {
    }

    public record SignUpRequest(String username, String password, String firstName, String lastName) {
    }

    public record Response(String token) {
    }
}