package com.example.sockettest.party.service;

import com.example.sockettest.party.components.OttAuthComponent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class PartyService {

    private final Map<String, OttAuthComponent> ottAuthMap;

    public String verifyOtt(String id, String password, String ottType) {
        return ottAuthMap.get(ottType).verifyOttAccount(id, password);
    }
}
