package com.sayedbaladoh.service;

import com.sayedbaladoh.config.JwtTokenProvider;
import com.sayedbaladoh.dto.AuthDTO;
import com.sayedbaladoh.dto.UserDTO;
import com.sayedbaladoh.enums.RoleName;
import com.sayedbaladoh.model.Role;
import com.sayedbaladoh.model.User;
import com.sayedbaladoh.repository.RoleRepository;
import com.sayedbaladoh.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationServiceImpl implements AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Override
    public AuthDTO.Response login(AuthDTO.LoginRequest loginDto) {

        var authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(
                        loginDto.username(),
                        loginDto.password()
                ));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtTokenProvider.generateToken(authentication);

        return new AuthDTO.Response(token);
    }

    @Override
    public UserDTO.Response signup(AuthDTO.SignUpRequest signUpRequest) {
        return signup(signUpRequest, Set.of(getRole(RoleName.ROLE_USER)));
    }

    @Override
    public UserDTO.Response signupAdmin(AuthDTO.SignUpRequest signUpRequest) {
        return signup(signUpRequest, Set.of(getRole(RoleName.ROLE_ADMIN)));
    }

    private UserDTO.Response signup(AuthDTO.SignUpRequest signUpRequest, Set<Role> roles) {
        User user = User
                .builder()
                .firstName(signUpRequest.firstName())
                .lastName(signUpRequest.lastName())
                .username(signUpRequest.username())
                .password(passwordEncoder.encode(signUpRequest.password()))
                .roles(roles)
                .build();
        userRepository.save(user);

        return new UserDTO.Response(user.getId(), user.getFirstName(), user.getLastName(), user.getUsername(), user.getCreatedAt(), user.getUpdatedAt());
    }

    private Role getRole(RoleName name) {
        log.info("Fetching role for name: {}.", name);
        return roleRepository
                .findByName(name)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Role is not found with name: %s.", name)));
    }
}
