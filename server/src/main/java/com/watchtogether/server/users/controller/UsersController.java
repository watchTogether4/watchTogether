package com.watchtogether.server.users.controller;


import com.watchtogether.server.components.MailComponents;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UsersController {

    private final MailComponents mailComponents;

    @GetMapping("/email")
    public ResponseEntity<?> sendEmail(@RequestParam("email") String email,
        @RequestParam("subject") String subject,
        @RequestParam("text") String text) {

        return ResponseEntity.ok(mailComponents.sendMail(email, subject, text));

    }


}
