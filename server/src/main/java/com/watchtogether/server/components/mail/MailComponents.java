package com.watchtogether.server.components.mail;

import javax.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class MailComponents {

    public final JavaMailSender javaMailSender;

    public boolean sendMail(String mail, String subject, String text) {

        boolean result = false;

        MimeMessagePreparator msg = new MimeMessagePreparator() {
            @Override
            public void prepare(MimeMessage mimeMessage) throws Exception {
                MimeMessageHelper mimeMessageHelper =
                    new MimeMessageHelper(mimeMessage, true, "UTF-8");

                mimeMessageHelper.setTo(mail); // 받는 사람 메일 주소
                mimeMessageHelper.setSubject(subject);  // 메일 제목
                mimeMessageHelper.setText(text, true);  // 메일 본문 내용

            }
        };

        try {
            javaMailSender.send(msg);
            result = true;
        } catch (Exception e) {
            log.info(e.getMessage());
        }
        return result;
    }
}
