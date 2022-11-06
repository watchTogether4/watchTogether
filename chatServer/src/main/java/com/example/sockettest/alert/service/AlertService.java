package com.example.sockettest.alert.service;

import com.example.sockettest.alert.dto.AlertDto;
import com.example.sockettest.alert.persist.AlertRepository;
import com.example.sockettest.alert.persist.PartyRepository;
import com.example.sockettest.alert.persist.UserRepository;
import com.example.sockettest.alert.persist.entity.Notification;
import com.example.sockettest.alert.persist.entity.Party;
import com.example.sockettest.alert.persist.entity.User; // TODO: 2022/11/05 서버로 옮기고 경로 변경하기
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
                            String message, String type) {

        // TODO: 2022/11/06  실제 로직
//        List<User> users = userRepository.findAllByNicknameIn(nickNames);
//        Party party = partyRepository.findById(partyId).orElseThrow(() -> new RuntimeException("파티를 찾을 수 없습니다."));
//
//        List<Notification> notifications = new ArrayList<>();
//        for (User user : users) {
//            notifications.add(new Notification(user.getEmail(), party, inviteId, message, type));
//        }
//        alertRepository.saveAll(notifications);

        Party party = new Party();
        List<Notification> notifications = new ArrayList<>();
        for (String nickName : nickNames) {
            notifications.add(new Notification(nickName, party, inviteId, message, type));
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
