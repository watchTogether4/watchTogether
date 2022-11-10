package com.watchtogether.server.ott.controller;

import com.watchtogether.server.ott.domain.dto.LoginResult;
import com.watchtogether.server.ott.domain.dto.OttAuthRequest;
import com.watchtogether.server.ott.service.OttAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/ottAuth")

public class OttAuthController {

    private final OttAuthService partyService;

    @PostMapping
    public LoginResult verifyOtt(@RequestBody OttAuthRequest request) {
        return partyService.verifyOtt(request.getId(), request.getPassword(), request.getOttType());
    }
}
