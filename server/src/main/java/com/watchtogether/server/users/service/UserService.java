package com.watchtogether.server.users.service;

import com.watchtogether.server.users.domain.dto.UserDto;
import java.time.LocalDate;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    /**
     * 사용자 회원가입 신청
     *
     * @param email
     * @param nickname
     * @param password
     * @param birth
     * @return
     */
    UserDto singUpUser(String email, String nickname, String password, LocalDate birth);

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

    /**
     * 사용자 마이페이지
     *
     * @param email
     * @return
     */
    UserDto myPageUser(String email);

    /**
     * 사용자 닉네임 검색
     *
     * @param nickname 닉네임
     * @return
     */
    String searchNickname(String nickname);

    /**
     * 현재 패스워드 검사
     *
     * @param email    이메일(아이디)
     * @param password 현재 패스워드
     */
    void checkPassword(String email, String password);
}
