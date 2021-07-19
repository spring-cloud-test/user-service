package com.example.userservice.controller;

import com.example.userservice.component.Greeting;
import com.netflix.discovery.converters.Auto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/user-service")
public class UserController {

    private final Greeting greeting;

    @Autowired
    public UserController(Greeting greeting) {
        this.greeting = greeting;
    }

    @GetMapping(value = "/health-check")
    public String status() {
        return "It's working in user service";
    }

    @GetMapping(value = "/welcome")
    public String welcome() {
        return greeting.getMessage();
    }

}
