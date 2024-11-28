package com.sayedbaladoh.controller;

import com.sayedbaladoh.dto.AuthDTO;
import com.sayedbaladoh.dto.UserDTO;
import com.sayedbaladoh.service.AuthenticationService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@Slf4j
@AllArgsConstructor
public class AuthController {
    @Autowired
    private AuthenticationService authService;

    @PostMapping("/login")
    public ResponseEntity<AuthDTO.Response> login(@RequestBody AuthDTO.LoginRequest loginDto) {

        AuthDTO.Response response = authService.login(loginDto);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/signup")
    public ResponseEntity<UserDTO.Response> register(@RequestBody AuthDTO.SignUpRequest signUpRequest) {
        UserDTO.Response response = authService.signup(signUpRequest);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/admins")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<UserDTO.Response> registerAdmin(@RequestBody AuthDTO.SignUpRequest signUpRequest) {
        UserDTO.Response response = authService.signupAdmin(signUpRequest);
        return ResponseEntity.ok(response);
    }
}
