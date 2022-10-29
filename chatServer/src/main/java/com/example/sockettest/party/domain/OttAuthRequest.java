package com.example.sockettest.party.domain;

import lombok.Data;

@Data
public class OttAuthRequest {

    private String id;
    private String password;
    private String ottType;
}
