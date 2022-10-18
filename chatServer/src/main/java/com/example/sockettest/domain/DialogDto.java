package com.example.sockettest.domain;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class DialogDto {
    private LocalDateTime date;
    private String sender;
    private String message;



}
