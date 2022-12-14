package com.watchtogether.server.alert.service;


import com.watchtogether.server.alert.dto.AlertDto;
import com.watchtogether.server.alert.dto.CheckAlertResponse;
import com.watchtogether.server.alert.persist.AlertRepository;
import com.watchtogether.server.alert.persist.entity.Notification;
import com.watchtogether.server.exception.AlertException;
import com.watchtogether.server.exception.PartyException;
import com.watchtogether.server.exception.type.AlertErrorCode;
import com.watchtogether.server.exception.type.PartyErrorCode;
import com.watchtogether.server.party.domain.entitiy.Party;
import com.watchtogether.server.party.domain.entitiy.PartyMember;
import com.watchtogether.server.party.domain.repository.PartyRepository;
import com.watchtogether.server.party.domain.type.AlertType;
import com.watchtogether.server.users.domain.entitiy.User;
import com.watchtogether.server.users.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.watchtogether.server.exception.type.AlertErrorCode.NOT_FOUND_NOTIFICATION;
import static com.watchtogether.server.exception.type.PartyErrorCode.NOT_FOUND_PARTY;


@Service
@RequiredArgsConstructor
public class AlertService {

    private final AlertRepository alertRepository;
    private final UserRepository userRepository;
    private final PartyRepository partyRepository;


    public void createAlert(List<String> nickNames, long partyId, String inviteId,
                            String message, AlertType type) {

        List<User> users = userRepository.findAllByNicknameIn(nickNames);
        Party party = partyRepository.findById(partyId).orElseThrow(() -> new PartyException(NOT_FOUND_PARTY));

        List<Notification> notifications = new ArrayList<>();
        for (User user : users) {
            notifications.add(new Notification(user.getEmail(), party, inviteId, message, type));
        }
        alertRepository.saveAll(notifications);

    }

    @Transactional
    public CheckAlertResponse checkAlert(String notificationId) {
        Notification notification = alertRepository.findById(Long.parseLong(notificationId))
                .orElseThrow(()->new AlertException(NOT_FOUND_NOTIFICATION));

        notification.setCheckAlert(true);
        alertRepository.save(notification);

        return new CheckAlertResponse(notification.getParty().getId());
    }

    public List<AlertDto> getAlertList(String email) {
        List<Notification> notifications = alertRepository.findAllByEmail(email);

        return notifications.stream()
                .map(m -> new AlertDto(m))
                .collect(Collectors.toList());
    }

    public ResponseEntity<Object> deleteAlert(Party party){
        List<Notification> list = alertRepository.findByParty(party);
        if (!list.isEmpty()){
            alertRepository.deleteAll(list);
        }
        return ResponseEntity.ok().build();
    }

    @Transactional
    @Scheduled(cron = "0 0 0 * * *")
    public void deleteAlert() {
        List<Notification> notifications = alertRepository
                .findByExpiredDtLessThanEqual(LocalDateTime.now());

        alertRepository.deleteAllInBatch(notifications);
    }

    public void deleteAlert(long notificationId) {
        alertRepository.deleteById(notificationId);
    }

    @Transactional
    public void deleteAlertMemberBeforeUserLeave(String email){
        alertRepository.deleteAllByEmail(email);
    }

    @Transactional
    public void deleteAlertLeaderBeforeUserLeave(Party party){
        alertRepository.deleteAllByParty(party);
    }


}
