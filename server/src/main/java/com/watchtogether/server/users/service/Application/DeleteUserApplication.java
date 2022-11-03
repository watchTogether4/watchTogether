package com.watchtogether.server.users.service.Application;

import com.watchtogether.server.party.service.PartyService;
import com.watchtogether.server.users.domain.dto.UserDto;
import com.watchtogether.server.users.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteUserApplication {

    private final UserService userService;
    private final PartyService partyService;

    public void deleteUser(String email, String password) {

        // 회원 아이디, 비밀번호, 잔액 검사
        UserDto user = userService.checkUserAndCash(email, password);

        // 자신이 속한 파티 그룹 검사
        partyService.findMyPartiesBeforeDeleteUser(user.getNickname());

        // 사용자 파티 회원 탈퇴
        userService.deleteUser(email);

    }
}
