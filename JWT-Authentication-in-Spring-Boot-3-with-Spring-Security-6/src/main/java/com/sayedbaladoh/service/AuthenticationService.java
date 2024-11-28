package com.sayedbaladoh.service;

import com.sayedbaladoh.dto.AuthDTO;
import com.sayedbaladoh.dto.UserDTO;

public interface AuthenticationService {
    AuthDTO.Response login(AuthDTO.LoginRequest loginRequest);

    UserDTO.Response signup(AuthDTO.SignUpRequest signUpRequest);

    UserDTO.Response signupAdmin(AuthDTO.SignUpRequest signUpRequest);
}
