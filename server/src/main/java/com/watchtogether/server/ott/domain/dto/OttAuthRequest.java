package com.watchtogether.server.ott.domain.dto;

import lombok.Data;

@Data
public class OttAuthRequest {

    private String id;
    private String password;
    private String ottType;
}
