package com.sayedbaladoh.service;

import com.sayedbaladoh.dto.UserDTO;

import java.util.List;

public interface UserService {
    List<UserDTO.Response> getAll();
}
