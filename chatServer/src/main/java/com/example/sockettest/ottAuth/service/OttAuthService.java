package com.example.sockettest.ottAuth.service;

import com.example.sockettest.ottAuth.components.OttAuthComponent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@RequiredArgsConstructor
@Service
public class OttAuthService {

    private final Map<String, OttAuthComponent> ottAuthMap;

    public String verifyOtt(String partyId, String id, String password, String ottType) {
        return ottAuthMap.get(ottType).verifyOttAccount(id, password);
    }
}
