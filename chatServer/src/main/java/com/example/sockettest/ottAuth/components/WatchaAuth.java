package com.example.sockettest.ottAuth.components;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component(value = "WATCHA")
public class WatchaAuth implements OttAuthComponent{

    private static final String LOGIN_URL = "https://watcha.com/sign_in";

    @Override
    public String verifyOttAccount(String id, String password) {
        return "1";
    }
}
