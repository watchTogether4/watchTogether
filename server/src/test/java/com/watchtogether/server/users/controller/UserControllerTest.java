package com.watchtogether.server.users.controller;

import static com.watchtogether.server.exception.type.UserErrorCode.ALREADY_SIGNUP_EMAIL;
import static com.watchtogether.server.exception.type.UserErrorCode.ALREADY_SIGNUP_NICKNAME;
import static com.watchtogether.server.exception.type.UserErrorCode.LEAVE_USER;
import static com.watchtogether.server.exception.type.UserErrorCode.NEED_VERIFY_EMAIL;
import static com.watchtogether.server.exception.type.UserErrorCode.NOT_FOUND_USER;
import static com.watchtogether.server.exception.type.UserErrorCode.WRONG_PASSWORD_USER;
import static com.watchtogether.server.users.domain.type.UserSuccess.SUCCESS_SIGNIN;
import static com.watchtogether.server.users.domain.type.UserSuccess.SUCCESS_SIGNUP;
import static com.watchtogether.server.users.domain.type.UserSuccess.SUCCESS_VERIFY_EMAIL;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.watchtogether.server.exception.UserException;
import com.watchtogether.server.users.domain.dto.UserDto;
import com.watchtogether.server.users.domain.model.SignInUser;
import com.watchtogether.server.users.domain.model.SignUpUser;
import com.watchtogether.server.users.domain.type.UserStatus;
import com.watchtogether.server.users.service.UserService;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(UserController.class)
@MockBean(JpaMetamodelMappingContext.class)
class UserControllerTest {

    @MockBean
    private UserService userService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;


    private static final String RANDOM_CODE = getRandomCode();
    private static final DateTimeFormatter testDate = DateTimeFormatter.ofPattern("yyyy-MM-dd");


    @DisplayName("성공 케이스_사용자 회원가입 신청")
    @Test
    void successSignUp() throws Exception {
        //given
        given(userService.singUpUser(anyString(), anyString(), anyString(), any()))
            .willReturn(UserDto.builder()
                .email("test@gmail.com")
                .nickname("apple")
                .password("password")
                .birth(LocalDate.parse("2222-02-22", testDate))
                .cash(0L)
                .emailVerify(false)
                .emailVerifyCode(getRandomCode())
                .emailVerifyExpiredDt(LocalDateTime.now().plusDays(1))
                .status(UserStatus.REQ)
                .createdDt(LocalDateTime.now())
                .updatedDt(LocalDateTime.now())
                .build());

        //when
        //then
        mockMvc.perform(post("/api/users/signUp")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(
                    new SignUpUser.Request(
                        "test@gmail.com"
                        , "apple"
                        , "password"
                        , LocalDate.parse("2222-02-22", testDate))
                )))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.email").value("test@gmail.com"))
            .andExpect(jsonPath("$.message").value(SUCCESS_SIGNUP.getMessage()))
            .andDo(print());

    }

    @DisplayName("실패 케이스_중복 이메일_사용자 회원가입 신청")
    @Test
    void failureDuplicationEmailSignUp() throws Exception {
        //given
        given(userService.singUpUser(anyString(), anyString(), anyString(), any()))
            .willThrow(new UserException(ALREADY_SIGNUP_EMAIL));

        //when
        //then
        mockMvc.perform(post("/api/users/signUp")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(
                    new SignUpUser.Request(
                        "test@gmail.com"
                        , "apple"
                        , "password"
                        , LocalDate.parse("2222-02-22", testDate))
                )))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.userErrorCode").value("ALREADY_SIGNUP_EMAIL"))
            .andExpect(jsonPath("$.errorMessage").value(ALREADY_SIGNUP_EMAIL.getDetail()))
            .andDo(print());


    }

    @DisplayName("실패 케이스_중복 닉네임_사용자 회원가입 신청")
    @Test
    void failureDuplicationNickNameSignUp() throws Exception {
        //given
        given(userService.singUpUser(anyString(), anyString(), anyString(), any()))
            .willThrow(new UserException(ALREADY_SIGNUP_NICKNAME));

        //when
        //then
        mockMvc.perform(post("/api/users/signUp")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(
                    new SignUpUser.Request(
                        "test@gmail.com"
                        , "apple"
                        , "password"
                        , LocalDate.parse("2222-02-22", testDate))
                )))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.userErrorCode").value("ALREADY_SIGNUP_NICKNAME"))
            .andExpect(jsonPath("$.errorMessage").value(ALREADY_SIGNUP_NICKNAME.getDetail()))
            .andDo(print());
    }

    @DisplayName("성공 케이스_인증 메일 검증")
    @Test
    void successVerifyEmail() throws Exception {
        //given
        given(userService.singUpUser(anyString(), anyString(), anyString(), any()))
            .willReturn(UserDto.builder()
                .email("test@gmail.com")
                .nickname("apple")
                .password("password")
                .birth(LocalDate.parse("2222-02-22", testDate))
                .cash(0L)
                .emailVerify(false)
                .emailVerifyCode(RANDOM_CODE)
                .emailVerifyExpiredDt(LocalDateTime.now().plusDays(1))
                .status(UserStatus.REQ)
                .createdDt(LocalDateTime.now())
                .updatedDt(LocalDateTime.now())
                .build());
        //when
        //then
        mockMvc.perform(
                get("/api/users/signUp/verify/?email='test@gmail.com'&code='" + RANDOM_CODE + "'"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.message").value(SUCCESS_VERIFY_EMAIL.getMessage()))
            .andDo(print());

    }

    @Test
    @DisplayName("성공 케이스_사용자 로그인")
    void successSignIn() throws Exception {
        //given
        given(userService.signInUser(anyString(), anyString()))
            .willReturn(UserDto.builder()
                .email("test@gmail.com")
                .nickname("apple")
                .password("password")
                .birth(LocalDate.parse("2222-02-22", testDate))
                .cash(0L)
                .emailVerify(false)
                .emailVerifyCode(RANDOM_CODE)
                .emailVerifyExpiredDt(LocalDateTime.now().plusDays(1))
                .status(UserStatus.REQ)
                .lastLoginDt(LocalDateTime.now())
                .createdDt(LocalDateTime.now())
                .updatedDt(LocalDateTime.now())
                .build());
        //when
        //then
        mockMvc.perform(post("/api/users/signIn")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(
                    new SignInUser.Request(
                        "test@gmail.com"
                        , "password")
                )))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.email").value("test@gmail.com"))
            .andExpect(jsonPath("$.message").value(SUCCESS_SIGNIN.getMessage()))
            .andDo(print());
    }

    @Test
    @DisplayName("실패 케이스_일치하는 사용자 없음_사용자 로그인")
    void failureNotFoundUserSignIn() throws Exception {
        //given
        given(userService.signInUser(anyString(), anyString()))
            .willThrow(new UserException(NOT_FOUND_USER));
        //when
        //then
        mockMvc.perform(post("/api/users/signIn")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(
                    new SignInUser.Request(
                        "wrong@gmail.com"
                        , "password")
                )))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.userErrorCode").value("NOT_FOUND_USER"))
            .andExpect(jsonPath("$.errorMessage").value(NOT_FOUND_USER.getDetail()))
            .andDo(print());
    }

    @Test
    @DisplayName("실패 케이스_패스워드가 일치하지 않음_사용자 로그인")
    void failureWrongPasswordSignIn() throws Exception {
        //given
        given(userService.signInUser(anyString(), anyString()))
            .willThrow(new UserException(WRONG_PASSWORD_USER));
        //when
        //then
        mockMvc.perform(post("/api/users/signIn")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(
                    new SignInUser.Request(
                        "test@gmail.com"
                        , "wrongPassword")
                )))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.userErrorCode").value("WRONG_PASSWORD_USER"))
            .andExpect(jsonPath("$.errorMessage").value(WRONG_PASSWORD_USER.getDetail()))
            .andDo(print());
    }

    @Test
    @DisplayName("실패 케이스_탈퇴한 사용자_사용자 로그인")
    void failureLeaveUserSignIn() throws Exception {
        //given
        given(userService.signInUser(anyString(), anyString()))
            .willThrow(new UserException(LEAVE_USER));
        //when
        //then
        mockMvc.perform(post("/api/users/signIn")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(
                    new SignInUser.Request(
                        "test@gmail.com"
                        , "Password")
                )))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.userErrorCode").value("LEAVE_USER"))
            .andExpect(jsonPath("$.errorMessage").value(LEAVE_USER.getDetail()))
            .andDo(print());
    }

    @Test
    @DisplayName("실패 케이스_이메일 인증 필요_사용자 로그인")
    void failureNeedVerifyEmailSignIn() throws Exception {
        //given
        given(userService.signInUser(anyString(), anyString()))
            .willThrow(new UserException(NEED_VERIFY_EMAIL));
        //when
        //then
        mockMvc.perform(post("/api/users/signIn")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(
                    new SignInUser.Request(
                        "test@gmail.com"
                        , "Password")
                )))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.userErrorCode").value("NEED_VERIFY_EMAIL"))
            .andExpect(jsonPath("$.errorMessage").value(NEED_VERIFY_EMAIL.getDetail()))
            .andDo(print());
    }

    private static String getRandomCode() {
        return RandomStringUtils.random(15, true, true);
    }
}