package com.watchtogether.server.alert.controller;



import com.watchtogether.server.alert.dto.AlertDto;
import com.watchtogether.server.alert.dto.CheckAlertRequest;
import com.watchtogether.server.alert.dto.CreateAlertRequest;
import com.watchtogether.server.alert.dto.GetAlertListRequest;
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
    void checkAlert(@RequestBody CheckAlertRequest request) {
        alertService.checkAlert(request.getNotificationId());
    }

    @GetMapping("/list")
    List<AlertDto> getAlertList(@RequestParam String email) {
        return alertService.getAlertList(email);
    }
}

