package com.sayedbaladoh.service;

import com.sayedbaladoh.dto.UserDTO;
import com.sayedbaladoh.model.User;
import com.sayedbaladoh.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public List<UserDTO.Response> getAll() {
        List<User> users = userRepository.findAll();

        return users.stream().map(user -> new UserDTO.Response(user.getId(), user.getFirstName(), user.getLastName(), user.getUsername(), user.getCreatedAt(), user.getUpdatedAt())).toList();
    }
}
