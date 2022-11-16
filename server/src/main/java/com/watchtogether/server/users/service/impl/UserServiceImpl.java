package com.watchtogether.server.users.service.impl;

import static com.watchtogether.server.exception.type.UserErrorCode.ALREADY_SIGNUP_EMAIL;
import static com.watchtogether.server.exception.type.UserErrorCode.ALREADY_SIGNUP_NICKNAME;
import static com.watchtogether.server.exception.type.UserErrorCode.ALREADY_VERIFY_EMAIL;
import static com.watchtogether.server.exception.type.UserErrorCode.EXPIRED_VERIFY_EMAIL_CODE;
import static com.watchtogether.server.exception.type.UserErrorCode.IS_EXIST_BALANCE;
import static com.watchtogether.server.exception.type.UserErrorCode.LEAVE_USER;
import static com.watchtogether.server.exception.type.UserErrorCode.NEED_VERIFY_EMAIL;
import static com.watchtogether.server.exception.type.UserErrorCode.NOT_FOUND_NICKNAME;
import static com.watchtogether.server.exception.type.UserErrorCode.NOT_FOUND_USER;
import static com.watchtogether.server.exception.type.UserErrorCode.SAME_PASSWORD;
import static com.watchtogether.server.exception.type.UserErrorCode.WRONG_PASSWORD_USER;
import static com.watchtogether.server.exception.type.UserErrorCode.WRONG_VERIFY_EMAIL_CODE;
import static com.watchtogether.server.users.domain.type.Authority.USER;

import com.watchtogether.server.components.mail.MailComponents;
import com.watchtogether.server.exception.UserException;
import com.watchtogether.server.users.domain.dto.UserDto;
import com.watchtogether.server.users.domain.entitiy.User;
import com.watchtogether.server.users.domain.repository.UserRepository;
import com.watchtogether.server.users.domain.type.UserStatus;
import com.watchtogether.server.users.service.UserService;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Locale;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final MailComponents mailComponents;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new UserException(NOT_FOUND_USER));
        return user;
    }

    @Override
    @Transactional
    public UserDto singUpUser(String email, String nickname, String password, LocalDate birth) {

        boolean existEmail = userRepository.existsById(email.toLowerCase(Locale.ROOT));

        if (existEmail) {
            throw new UserException(ALREADY_SIGNUP_EMAIL);
        }

        boolean existNick = userRepository.existsByNickname(nickname);
        if (existNick) {
            throw new UserException(ALREADY_SIGNUP_NICKNAME);
        }

        // 패스워드 암호화
        String encodePassword = passwordEncoder.encode(password);

        // 인증 메일 고유식별번호
        String code = getRandomCode();

        User user = userRepository.save(User.builder()
            .email(email.toLowerCase(Locale.ROOT))
            .nickname(nickname)
            .password(encodePassword)
            .birth(birth)
            .emailVerifyCode(code)
            .emailVerifyExpiredDt(LocalDateTime.now().plusDays(1))
            .status(UserStatus.REQ)
            .roles(USER.getRoles())
            .build());

        mailComponents.sendAuthEmail(email, code);

        return UserDto.fromEntity(user);
    }

    @Override
    @Transactional
    public void verifyUser(String email, String code) {

        User user = userRepository.findById(email)
            .orElseThrow(() -> new UserException(NOT_FOUND_USER));

        if (user.isEmailVerify()) {
            throw new UserException(ALREADY_VERIFY_EMAIL);
        } else if (!user.getEmailVerifyCode().equals(code)) {
            throw new UserException(WRONG_VERIFY_EMAIL_CODE);
        } else if (user.getEmailVerifyExpiredDt().isBefore(LocalDateTime.now())) {
            throw new UserException(EXPIRED_VERIFY_EMAIL_CODE);
        }

        // 이메일 인증
        user.setEmailVerify(true);
        // 사용자 상태
        user.setStatus(UserStatus.ING);
    }

    @Override
    @Transactional
    public UserDto signInUser(String email, String password) {

        User user = userRepository.findById(email)
            .orElseThrow(() -> new UserException(NOT_FOUND_USER));

        if (user.getStatus().equals(UserStatus.LEAVE)) {
            throw new UserException(LEAVE_USER);
        } else if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new UserException(WRONG_PASSWORD_USER);
        } else if (!user.isEmailVerify()) {
            throw new UserException(NEED_VERIFY_EMAIL);
        }

        // 마지막 로그인 날짜 저장
        user.setLastLoginDt(LocalDateTime.now());

        return UserDto.fromEntity(user);
    }

    @Override
    @Transactional
    public void deleteUser(String email) {

        User user = userRepository.findById(email)
            .orElseThrow(() -> new UserException(NOT_FOUND_USER));

        userRepository.delete(user);

    }


    @Override
    public UserDto InfoUser(String email) {

        User user = userRepository.findById(email)
            .orElseThrow(() -> new UserException(NOT_FOUND_USER));

        return UserDto.fromEntity(user);
    }

    @Override
    public String searchNickname(String nickname) {

        boolean existNickname = userRepository.existsByNickname(nickname);

        if (!existNickname) {
            throw new UserException(NOT_FOUND_NICKNAME);
        }

        return nickname;
    }

    @Override
    @Transactional
    public void resetPassword(String email, String nickname) {

        User user = userRepository.findByEmailAndNickname(email, nickname)
            .orElseThrow(() -> new UserException(NOT_FOUND_USER));

        // 랜덤 코드 생성
        String code = getRandomCode();

        user.setResetPasswordKey(code);
        user.setResetPasswordLimitDt(LocalDateTime.now().plusDays(1));

        mailComponents.sendResetPasswordEmail(email, code);

    }

    @Override
    @Transactional
    public void authResetPassword(String code) {

        User user = userRepository.findByResetPasswordKey(code)
            .orElseThrow(() -> new UserException(NOT_FOUND_USER));

        if (user.getResetPasswordLimitDt().isBefore(LocalDateTime.now()) ||
            user.getResetPasswordLimitDt() == null) {
            throw new UserException(EXPIRED_VERIFY_EMAIL_CODE);
        }
    }

    @Override
    @Transactional
    public void updateNewPassword(String code, String password) {

        User user = userRepository.findByResetPasswordKey(code)
            .orElseThrow(() -> new UserException(NOT_FOUND_USER));

        // 패스워드 초기화 코드의 유효기간이 지난 경우
        if (user.getResetPasswordLimitDt().isBefore(LocalDateTime.now()) ||
            user.getResetPasswordLimitDt() == null) {
            throw new UserException(EXPIRED_VERIFY_EMAIL_CODE);
        }

        // 기존 패스워드와 동일한지 여부 검사
        if (passwordEncoder.matches(password, user.getPassword())) {
            throw new UserException(SAME_PASSWORD);
        }

        //패스워드 암호화
        String encodePassword = passwordEncoder.encode(password);

        user.setPassword(encodePassword);
        user.setResetPasswordKey("");
        user.setResetPasswordLimitDt(null);

    }

    @Override
    @Transactional
    public void saveRefreshToken(String email, String refreshToken) {
        User user = userRepository.findById(email)
            .orElseThrow(() -> new UserException(NOT_FOUND_USER));

        user.setRefreshToken(refreshToken);
    }

    @Override
    public UserDto checkUser(String email) {
        User user = userRepository.findById(email)
            .orElseThrow(() -> new UserException(NOT_FOUND_USER));

        return UserDto.fromEntity(user);
    }


    @Override
    public UserDto checkPassword(String email, String password) {
        User user = userRepository.findById(email)
            .orElseThrow(() -> new UserException(NOT_FOUND_USER));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new UserException(WRONG_PASSWORD_USER);
        }

        return UserDto.fromEntity(user);
    }

    @Override
    @Transactional
    public void updateUserPassword(String email, String password) {
        User user = userRepository.findById(email)
            .orElseThrow(() -> new UserException(NOT_FOUND_USER));

        // 기존 패스워드와 동일한지 여부 검사
        if (passwordEncoder.matches(password, user.getPassword())) {
            throw new UserException(SAME_PASSWORD);
        }

        // 패스워드 암호화
        String encodePassword = passwordEncoder.encode(password);

        user.setPassword(encodePassword);

    }


    /**
     * 문자와 숫자로 조합된 15자리의 코드 생성
     *
     * @return 문자와 숫자로 조합된 15자리의 코드
     */
    private String getRandomCode() {
        return RandomStringUtils.random(15, true, true);
    }
}
