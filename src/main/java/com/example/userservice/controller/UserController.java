package com.example.userservice.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/user-service")
public class UserController {

    @GetMapping(value = "/health-check")
    public String status() {
        return "It's working in user service";
    }

    @GetMapping(value = "/message")
    public String message(@RequestHeader("user-request") String header) {
        log.info("user-service: {}", header);
        return "Hello world user-service";
    }

}
