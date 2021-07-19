package com.example.userservice.domain;

import lombok.Data;

@Data
public class ResponseUser {
    private String email;
    private String name;
    private String userId;
}
