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

    /**
     * 회원가입 인증 코드 메일 전송
     *
     * @param email 이메일
     * @param code 인증코드(15자리 랜덤문자)
     * @return
     */
    public boolean sendAuthEmail(String email, String code) {

        StringBuilder builder = new StringBuilder();
        String subject = "watchTogether 사이트 가입을 축하드립니다!";
        String text = builder.append("<p>안녕하세요.</p>")
            .append("<p>이메일 인증을 완료하기위해 아래 링크를 클릭해주세요!.</p>")
            .append("<div><a href='http://localhost:8081/api/v1/users/sign-up/verify/?email=")
            .append(email)
            .append("&code=")
            .append(code)
            .append("'>가입완료</a><div>")
            .toString();

        return sendMail(email, subject, text);
    }


    /**
     * 패스워드 초기화 코드 인증 메일 전송
     *
     * @param email 이메일
     * @param code 인증코드(15자리 랜덤문자)
     * @return
     */
    public boolean sendResetPasswordEmail(String email, String code) {

        StringBuilder builder = new StringBuilder();
        String subject = "[watchTogether] 비밀번호 초기화 메일 입니다.";
        String text = builder.append("<p>안녕하세요.</p>")
            .append("<p>watchTogether 비밀번호 초기화 메일 입니다.</p>")
            .append("<p>아래 코드를 복사해서 입력해주세요.</p>")
            .append(code)
            .toString();

        return sendMail(email, subject, text);
    }

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
