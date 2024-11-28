package com.sayedbaladoh.dto;


import java.util.Date;

public class UserDTO {
    public record Response(Long id, String firstName, String lastName, String username, Date createdAt,
                           Date updatedAt) {
    }
}