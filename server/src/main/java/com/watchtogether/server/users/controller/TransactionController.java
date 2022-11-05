package com.watchtogether.server.users.controller;

import static com.watchtogether.server.users.domain.type.TransactionSuccess.SUCCESS_CHARGE;

import com.watchtogether.server.users.domain.dto.TransactionDto;
import com.watchtogether.server.users.domain.model.TransactionCharge;
import com.watchtogether.server.users.service.TransactionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Tag(name = "거래(TransactionController)", description = "거래 API")
@RequestMapping("/api/v1/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    @PostMapping("/charge")
    @Operation(summary = "사용자 캐쉬 충전", description = "일정 금액에 대한 충전 요청.")
    public ResponseEntity<TransactionCharge.Response> cashCharge(
        @Validated @RequestBody TransactionCharge.Request request) {

        TransactionDto transactionDto = transactionService.userCashCharge(
            request.getEmail(), request.getPassword(), request.getAmount());

        return ResponseEntity.ok(
            new TransactionCharge.Response(
                transactionDto.getEmail()
                , transactionDto.getTransactionType().getDescription()
                , transactionDto.getTransactionResultType().getDescription()
                , transactionDto.getAmount()
                , transactionDto.getBalanceSnapshot()
                , transactionDto.getTraderNickname()
                , transactionDto.getTransactionDt()
                , SUCCESS_CHARGE.getMessage()
            )
        );
    }
}
