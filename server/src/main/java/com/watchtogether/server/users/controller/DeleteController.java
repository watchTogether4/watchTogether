package com.watchtogether.server.users.controller;

import com.watchtogether.server.users.domain.model.DeleteUser;
import com.watchtogether.server.users.domain.model.DeleteUser.Response;
import com.watchtogether.server.users.domain.type.UserSuccess;
import com.watchtogether.server.users.service.Application.DeleteUserApplication;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@Tag(name = "사용자(UserController)", description = "사용자 API")
@RequestMapping("/api/v1/users")
public class DeleteController {

    private final DeleteUserApplication deleteUserApplication;

    @DeleteMapping
    @Operation(summary = "사용자 회원 탈퇴 요청", description = "사용자는 회원 탈퇴 요청을 보낸다.")
    public ResponseEntity<Response> deleteUser(
        @Validated @RequestBody DeleteUser.Request request) {

        deleteUserApplication.deleteUser(request.getEmail(), request.getPassword());

        return ResponseEntity.ok(
            new DeleteUser.Response(
                UserSuccess.SUCCESS_DELETE_USER.getMessage()));
    }

}
