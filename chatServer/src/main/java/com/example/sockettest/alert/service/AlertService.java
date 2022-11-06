package com.example.sockettest.alert.service;

import com.example.sockettest.alert.dto.AlertDto;
import com.example.sockettest.alert.persist.AlertRepository;
import com.example.sockettest.alert.persist.UserRepository;
import com.example.sockettest.alert.persist.entity.Notification;
import com.example.sockettest.alert.persist.entity.User; // TODO: 2022/11/05 서버로 옮기고 경로 변경하기
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.ast.Literal;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManagerFactory;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class AlertService {

    private final AlertRepository alertRepository;
    private final UserRepository userRepository;

    public void createAlert(String nickName, String partyId, String inviteId,
                            String message, String type) {

        //User user = userRepository.findByNickname(nickName).orElseThrow(() -> new RuntimeException("유저를 찾을 수 없습니다."));
        alertRepository.save(new Notification("user.getEmail()", partyId, inviteId, message, type));
    }

    @Transactional
    public void checkAlert(String notificationId) {
        Notification notification = alertRepository.findById(Long.parseLong(notificationId)).orElseThrow(()->new RuntimeException("알림을 찾을 수 없습니다."));
        notification.setCheckAlert(true);
    }

    public List<AlertDto> getAlertList(String email) {
        List<Notification> notifications = alertRepository.findAllByEmail(email);
        return notifications.stream()
                .map(m -> new AlertDto(m))
                .collect(Collectors.toList());
    }
}
