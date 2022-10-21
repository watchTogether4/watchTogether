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

    /**
     * 인증 메일
     *
     * @param email
     * @param code
     */
    void verifyUser(String email, String code);

    /**
     * 사용자 로그인
     *
     * @param email
     * @param password
     * @return
     */
    UserDto signInUser(String email, String password);
}
