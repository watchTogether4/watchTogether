package com.example.sockettest.party.controller;

import com.example.sockettest.party.components.OttAuthComponent;
import com.example.sockettest.party.domain.OttAuthRequest;
import com.example.sockettest.party.service.PartyService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/parties")
public class PartyController {

    private final PartyService partyService;

    @PostMapping("/verify-ott")
    public String verifyOtt(@RequestBody OttAuthRequest request) {

        return partyService.verifyOtt(request.getId(), request.getPassword(), request.getOttType());
    }
}
