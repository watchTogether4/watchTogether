package com.watchtogether.server.users.service;

import com.watchtogether.server.users.domain.dto.UserDto;
import com.watchtogether.server.users.domain.entitiy.User;
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
    UserDto InfoUser(String email);

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
    UserDto checkPassword(String email, String password);


    /**
     * 새로운 패스워드로 변경
     *
     * @param password 새로운 패스워드
     */
    void updateUserPassword(String email, String password);

    /**
     * 사용자 비밀번호를 재설정 메일 전송
     *
     * @param email    이메일(아이디)
     * @param nickname 닉네임
     */
    void resetPassword(String email, String nickname);

    /**
     * 사용자 비밀번호 재설정 인증
     *
     * @param code 패스워드 초기화 인증 코드
     */
    void authResetPassword(String code);

    /**
     * 사용자 비밀번호 재설정
     *
     * @param code     패스워드 초기화 인증 코드
     * @param password 새로운 패스워드
     */
    void updateNewPassword(String code, String password);


    /**
     * refresh 토큰 값 저장
     *
     * @param email        아이디
     * @param refreshToken refresh 토큰 값
     */
    void saveRefreshToken(String email, String refreshToken);

    /**
     * 사용자 회원 탈퇴 하기 전, 이메일 패스워드 잔액 검사
     *
     * @param email    이메일
     * @param password 패스워드
     * @return
     */
    UserDto checkUser(String email, String password);

    /**
     * 사용자 회원 탈퇴
     *
     * @param email
     */
    void deleteUser(String email);

}
