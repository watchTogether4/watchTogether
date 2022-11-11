package com.watchtogether.server.party.domain.model;


import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChangePasswordForm {

    private String nickname;

    private Long partyId;

    private String password;

    private String newPassword;
}
