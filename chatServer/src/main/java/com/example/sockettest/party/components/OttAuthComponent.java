package com.example.sockettest.party.components;

import org.springframework.stereotype.Component;

@Component
public interface OttAuthComponent {

    String verifyOttAccount(String id, String password);

}
