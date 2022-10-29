package com.example.sockettest.party.components;


import org.springframework.stereotype.Component;

@Component
public class NetflexAuth implements OttAuthComponent{

    @Override
    public String verifyOttAccount(String id, String password) {
        return "Netflex";
    }
}
