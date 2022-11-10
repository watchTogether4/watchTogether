package com.watchtogether.server.alert.service;


import com.watchtogether.server.alert.dto.AlertDto;
import com.watchtogether.server.alert.persist.AlertRepository;
import com.watchtogether.server.alert.persist.entity.Notification;
import com.watchtogether.server.party.domain.entitiy.Party;
import com.watchtogether.server.party.domain.repository.PartyRepository;
import com.watchtogether.server.party.domain.type.AlertType;
import com.watchtogether.server.users.domain.entitiy.User;
import com.watchtogether.server.users.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class AlertService {

    private final AlertRepository alertRepository;
    private final UserRepository userRepository;
    private final PartyRepository partyRepository;


    public void createAlert(List<String> nickNames, long partyId, String inviteId,
                            String message, AlertType type) {

        List<User> users = userRepository.findAllByNicknameIn(nickNames);
        Party party = partyRepository.findById(partyId).orElseThrow(() -> new RuntimeException("파티를 찾을 수 없습니다."));

        List<Notification> notifications = new ArrayList<>();
        for (User user : users) {
            notifications.add(new Notification(user.getEmail(), party, inviteId, message, type));
        }
        alertRepository.saveAll(notifications);

    }

    @Transactional
    public void checkAlert(String notificationId) {
        Notification notification = alertRepository.findById(Long.parseLong(notificationId))
                .orElseThrow(()->new RuntimeException("알림을 찾을 수 없습니다."));

        notification.setCheckAlert(true);
    }

    public List<AlertDto> getAlertList(String email) {
        List<Notification> notifications = alertRepository.findAllByEmail(email);

        return notifications.stream()
                .map(m -> new AlertDto(m))
                .collect(Collectors.toList());
    }

    @Transactional
    @Scheduled(cron = "0 0 0 * * *")
    public void deleteAlert() {
        List<Notification> notifications = alertRepository
                .findByExpiredDtLessThanEqual(LocalDateTime.now());

        alertRepository.deleteAllInBatch(notifications);
    }
}
