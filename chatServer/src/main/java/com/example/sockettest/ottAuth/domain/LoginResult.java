package com.example.sockettest.ottAuth.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginResult {

    private String ottType;
    private String loginResult;
}
