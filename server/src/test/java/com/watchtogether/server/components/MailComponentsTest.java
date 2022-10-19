package com.watchtogether.server.components;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MailComponentsTest {

    @Autowired
    private MailComponents mailComponents;

    @DisplayName("성공 케이스_이메일 인증 보내기")
    @Test
    void successSendAuthEmail() {
        String mail = "sjy19910222@gmail.com";
        String subject = "test";
        String text = "test body";

        boolean result = mailComponents.sendMail(mail, subject, text);

        assertEquals(true, result);

    }

    @DisplayName("실패 케이스_이메일 인증 보내기")
    @Test
    void failureSendAuthEmail() {
        String mail = "asdsa";
        String subject = "test";
        String text = "test body";

        boolean result = mailComponents.sendMail(mail, subject, text);

        assertEquals(false, result);

    }
}