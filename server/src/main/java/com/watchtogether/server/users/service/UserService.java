package com.watchtogether.server.users.service;

import com.watchtogether.server.users.domain.dto.UserDto;
import java.util.Date;

public interface UserService {

    /**
     * 사용자 회원가입 신청
     *
     * @param email
     * @param nickname
     * @param password
     * @param birth
     * @return
     */
    UserDto singUpUser(String email, String nickname, String password, Date birth);
}
