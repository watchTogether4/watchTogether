package com.example.sockettest.domain;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Dialog {
    private LocalDateTime date;
    private String sender;
    private String message;
}
