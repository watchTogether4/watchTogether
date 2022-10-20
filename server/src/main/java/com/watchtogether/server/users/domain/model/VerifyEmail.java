package com.watchtogether.server.users.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class VerifyEmail {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Response{
        private String message;
    }

}
