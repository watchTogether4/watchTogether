package com.example.sockettest.alert.dto;


import lombok.Data;

@Data
public class CreateAlertRequest {

        private String nickName;
        private String partyId;
        private String inviteId;
        private String message;
        private String type;



}

