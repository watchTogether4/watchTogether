package com.example.sockettest.party.components;

import org.springframework.stereotype.Component;

@Component
public class DisneyAuth implements OttAuthComponent{

    @Override
    public String verifyOttAccount(String id, String password) {
        return "Disney";
    }
}
