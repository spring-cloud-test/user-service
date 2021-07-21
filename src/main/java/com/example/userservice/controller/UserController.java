package com.example.userservice.controller;

import com.example.userservice.component.Greeting;
import com.example.userservice.domain.UserDto;
import com.example.userservice.domain.UserEntity;
import com.example.userservice.dto.RequestUser;
import com.example.userservice.dto.ResponseUser;
import com.example.userservice.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/")
public class UserController {

    private final Environment environment;
    private final Greeting greeting;
    private final UserService userService;

    @Autowired
    public UserController(Environment environment, Greeting greeting, UserService userService) {
        this.environment = environment;
        this.greeting = greeting;
        this.userService = userService;
    }

    @GetMapping(value = "/health-check")
    public String status() {
        return String.format("It's working in user service "
                + "port(local.server.port) = " + environment.getProperty("local.server.port")
                + "\t port(server.port) = " + environment.getProperty("server.port")
                + "\t \twith token secret = " + environment.getProperty("token.secret")
                + "\t with token time =" + environment.getProperty("token.expiration_time"));
    }

    @GetMapping(value = "/welcome")
    public String welcome() {
        return greeting.getMessage();
    }

    @PostMapping("/users")
    public ResponseEntity<ResponseUser> createUser(@RequestBody RequestUser user) {

        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        UserDto userDto = mapper.map(user, UserDto.class);
        userService.createUser(userDto);

        ResponseUser responseUser = mapper.map(userDto, ResponseUser.class);

        return ResponseEntity.status(HttpStatus.CREATED).body(responseUser);
    }

    @GetMapping("/users")
    public ResponseEntity<List<ResponseUser>> getUsers() {
        Iterable<UserEntity> userList = userService.getUserByAll();

        List<ResponseUser> result = new ArrayList<>();
        for (UserEntity userEntity : userList) {
            result.add(new ModelMapper().map(userEntity, ResponseUser.class));
        }
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @GetMapping(value = "/users/{userId}")
    public ResponseEntity<ResponseUser> getUser(@PathVariable("userId") String userId) {
        UserDto userDto = userService.getUserByUserId(userId);
        ResponseUser responseUser = new ModelMapper().map(userDto, ResponseUser.class);

        return ResponseEntity.status(HttpStatus.OK).body(responseUser);
    }
}
