package com.watchtogether.server.ottAuth.controller;

import com.watchtogether.server.ottAuth.domain.LoginResult;
import com.watchtogether.server.ottAuth.domain.OttAuthRequest;
import com.watchtogether.server.ottAuth.service.OttAuthService;
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
