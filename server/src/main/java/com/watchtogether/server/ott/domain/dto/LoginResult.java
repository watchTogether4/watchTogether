package com.watchtogether.server.ott.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginResult {

    private String ottType;
    private String loginResult;
}
