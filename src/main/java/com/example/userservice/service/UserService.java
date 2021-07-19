package com.example.userservice.service;

import com.example.userservice.domain.UserDto;
import org.springframework.stereotype.Service;

public interface UserService {
    UserDto createUser(UserDto userDto);
}
