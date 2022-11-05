package com.example.sockettest.alert.controller;


import com.example.sockettest.alert.dto.AlertDto;
import com.example.sockettest.alert.dto.CheckAlertRequest;
import com.example.sockettest.alert.dto.CreateAlertRequest;
import com.example.sockettest.alert.dto.GetAlertListRequest;
import com.example.sockettest.alert.service.AlertService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/alert")
public class AlertController {

    private final AlertService alertService;

    @PostMapping
    void createAlert(@RequestBody CreateAlertRequest request) {
        alertService.createAlert(request.getNickName(), request.getPartyId(),
                request.getInviteId(), request.getMessage(), request.getType());
    }

    @PutMapping("/check")
    void checkAlert(@RequestBody CheckAlertRequest request) {
        alertService.checkAlert(request.getNotificationId());
    }

    @GetMapping("/list")
    List<AlertDto> getAlertList(@RequestBody GetAlertListRequest request) {
        return alertService.getAlertList(request.getEmail());
    }
}

