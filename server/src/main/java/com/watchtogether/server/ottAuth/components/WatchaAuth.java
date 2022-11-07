package com.watchtogether.server.ottAuth.components;

import org.springframework.stereotype.Component;

@Component(value = "WATCHA")
public class WatchaAuth implements OttAuthComponent{

    private static final String LOGIN_URL = "https://watcha.com/sign_in";

    @Override
    public String verifyOttAccount(String id, String password) {
        return "1";
    }
}
