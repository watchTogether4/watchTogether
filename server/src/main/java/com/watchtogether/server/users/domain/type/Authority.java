package com.watchtogether.server.users.domain.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Authority {

    USER("ROLE_USER","사용자 권한"),
    ADMIN("ROLE_ADMIN","관리자권한");

    private String roles;
    private String description;
}
