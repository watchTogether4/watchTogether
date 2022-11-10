package com.watchtogether.server.ott.service;

import com.watchtogether.server.ott.components.OttAuthComponent;
import com.watchtogether.server.ott.domain.dto.LoginResult;
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
