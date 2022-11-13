package com.watchtogether.server.alert.controller;



import com.watchtogether.server.alert.dto.*;
import com.watchtogether.server.alert.service.AlertService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "Alert(AlertController)", description = "Alert API")
@RequestMapping("/api/v1/alert")
public class AlertController {

    private final AlertService alertService;

    @PostMapping
    void createAlert(@RequestBody CreateAlertRequest request) {
        alertService.createAlert(request.getNickName(), request.getPartyId(),
                request.getInviteId(), request.getMessage(), request.getType());
    }

    @PutMapping("/check")
     CheckAlertResponse checkAlert(@RequestBody CheckAlertRequest request) {
        return alertService.checkAlert(request.getNotificationId());
    }

    @GetMapping("/list")
    List<AlertDto> getAlertList(@RequestParam String email) {
        return alertService.getAlertList(email);
    }

    @DeleteMapping
    void deleteAlert(@RequestBody DeleteAlertRequest request) {
        alertService.deleteAlert(request.getNotificationId());
    }
}

