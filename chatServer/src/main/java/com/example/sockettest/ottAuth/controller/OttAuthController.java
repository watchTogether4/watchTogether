package com.example.sockettest.ottAuth.controller;

import com.example.sockettest.ottAuth.domain.OttAuthRequest;
import com.example.sockettest.ottAuth.domain.LoginResult;
import com.example.sockettest.ottAuth.service.OttAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


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
