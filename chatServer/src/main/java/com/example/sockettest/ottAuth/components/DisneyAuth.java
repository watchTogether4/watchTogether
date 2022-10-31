package com.example.sockettest.ottAuth.components;

import org.springframework.stereotype.Component;

@Component(value = "DISNEY")
public class DisneyAuth implements OttAuthComponent{

    @Override
    public String verifyOttAccount(String id, String password) {
        return "Disney";
    }
}
