package com.example.sockettest.alert.dto;


import com.example.sockettest.alert.persist.entity.Notification;
import lombok.Data;

@Data
public class AlertDto {

        private Long notificationId;
        private String type;
        private String message;


        public AlertDto(Notification notification) {
                this.notificationId = notification.getId();
                this.type = notification.getType();
                this.message = notification.getMessage();
        }
}

