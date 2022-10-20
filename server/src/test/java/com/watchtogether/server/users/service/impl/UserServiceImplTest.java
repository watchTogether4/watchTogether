package com.watchtogether.server.users.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.watchtogether.server.users.domain.dto.UserDto;
import com.watchtogether.server.users.domain.entitiy.User;
import com.watchtogether.server.users.domain.repository.UserRepository;
import com.watchtogether.server.users.domain.type.UserStatus;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userServiceImpl;

    private static final String RANDOM_CODE = getRandomCode();
    private static final SimpleDateFormat testDate = new SimpleDateFormat("yyyy-MM-dd");


    @DisplayName("성공 케이스_사용자 회원가입 신청")
    @Test
    void successSingUpUser() throws Exception {
        LocalDateTime expireDate = LocalDateTime.now().plusDays(1);
        //given
        User user = User.builder()
            .email("test@gmail.com")
            .nickname("apple")
            .password("password")
            .birth(testDate.parse("2222-02-22"))
            .build();

        given(userRepository.save(any()))
            .willReturn(user.builder()
                .cash(0L)
                .emailVerify(false)
                .emailVerifyCode(RANDOM_CODE)
                .emailVerifyExpiredDt(expireDate)
                .status(UserStatus.REQ)
                .build());

        ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);

        //when
        UserDto userDto = userServiceImpl.singUpUser("test@gmail.com", "apple", "password",
            testDate.parse("2222-02-22"));

        //then
        verify(userRepository, times(1)).save(captor.capture());
        assertEquals("test@gmail.com", captor.getValue().getEmail());
        assertEquals("apple", captor.getValue().getNickname());
        assertEquals("password", captor.getValue().getPassword());
        assertEquals(testDate.parse("2222-02-22"), captor.getValue().getBirth());
        assertEquals(0L, captor.getValue().getCash());
        assertEquals(false, captor.getValue().isEmailVerify());
        assertEquals(RANDOM_CODE, userDto.getEmailVerifyCode());
        assertEquals(expireDate, userDto.getEmailVerifyExpiredDt());
        assertEquals(UserStatus.REQ, userDto.getStatus());
    }


    private static String getRandomCode() {
        return RandomStringUtils.random(15, true, true);
    }
}