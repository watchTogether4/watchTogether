package com.example.sockettest.ottAuth.service;

import com.example.sockettest.ottAuth.components.OttAuthComponent;
import com.example.sockettest.ottAuth.domain.LoginResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@RequiredArgsConstructor
@Service
public class OttAuthService {

    private final Map<String, OttAuthComponent> ottAuthMap;

    public LoginResult verifyOtt(String id, String password, String ottType) {
        return new LoginResult(ottType, ottAuthMap.get(ottType).verifyOttAccount(id, password));
    }
}
