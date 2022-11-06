package com.watchtogether.server.alert.dto;


import lombok.Data;

import java.util.List;

@Data
public class CreateAlertRequest {

        private List<String> nickName;
        private long partyId;
        private String inviteId;
        private String message;
        private String type;



}

