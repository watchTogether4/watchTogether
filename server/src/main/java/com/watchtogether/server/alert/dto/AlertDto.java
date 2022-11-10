package com.watchtogether.server.alert.dto;


import com.watchtogether.server.alert.persist.entity.Notification;
import com.watchtogether.server.party.domain.type.AlertType;
import lombok.Data;

@Data
public class AlertDto {

        private Long notificationId;
        private AlertType type;
        private String message;
        private boolean notificationOpen;
        private String uuid;


        public AlertDto(Notification notification) {
                this.notificationId = notification.getId();
                this.type = notification.getType();
                this.message = notification.getMessage();
                this.notificationOpen = notification.isCheckAlert();
                this.uuid = notification.getInviteId();
        }
}

