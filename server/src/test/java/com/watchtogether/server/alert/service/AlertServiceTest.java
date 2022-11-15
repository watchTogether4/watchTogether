package com.watchtogether.server.alert.service;

import com.watchtogether.server.alert.dto.CheckAlertResponse;
import com.watchtogether.server.alert.persist.AlertRepository;
import com.watchtogether.server.alert.persist.entity.Notification;
import com.watchtogether.server.party.domain.entitiy.Party;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class AlertServiceTest {

    @Mock
    private AlertRepository alertRepository;

    @InjectMocks
    private AlertService alertService;

    @Test
    void checkAlertSuccess() {
        //given
        Party party = Party.builder()
                .id(1L).build();

        given(alertRepository.findById(anyLong()))
                .willReturn(Optional.of(Notification.builder()
                        .id(1l)
                        .party(party)
                        .checkAlert(false)
                        .build()));

        ArgumentCaptor<Notification> captor = ArgumentCaptor.forClass(Notification.class);

        //when
        CheckAlertResponse checkAlertResponse = alertService.checkAlert("1");

        //then
        verify(alertRepository, times(1)).save(captor.capture());
        Assertions.assertEquals(true, captor.getValue().isCheckAlert());
        Assertions.assertEquals(1L, checkAlertResponse.getPartyId());
    }






}