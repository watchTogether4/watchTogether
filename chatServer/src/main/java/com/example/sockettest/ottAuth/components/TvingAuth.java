package com.example.sockettest.ottAuth.components;

import org.springframework.stereotype.Component;

@Component(value = "TVING")
public class TvingAuth implements OttAuthComponent{

    private static final String LOGIN_URL = "https://www.disneyplus.com/ko-kr/login";

    @Override
    public String verifyOttAccount(String id, String password) {
        return "1";
    }
}
