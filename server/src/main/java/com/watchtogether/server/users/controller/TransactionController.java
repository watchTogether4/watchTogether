package com.watchtogether.server.users.controller;

import static com.watchtogether.server.users.domain.type.TransactionSuccess.SUCCESS_CANCEL_PREPAYMENT;
import static com.watchtogether.server.users.domain.type.TransactionSuccess.SUCCESS_CHARGE;
import static com.watchtogether.server.users.domain.type.TransactionSuccess.SUCCESS_PREPAYMENT;
import static com.watchtogether.server.users.domain.type.TransactionSuccess.SUCCESS_TRANSACTION_LIST;

import com.watchtogether.server.users.domain.dto.TransactionDto;
import com.watchtogether.server.users.domain.entitiy.User;
import com.watchtogether.server.users.domain.model.TransactionCharge;
import com.watchtogether.server.users.domain.model.TransactionList;
import com.watchtogether.server.users.domain.model.TransactionWithdraw;
import com.watchtogether.server.users.service.Application.TransactionApplicaion;
import com.watchtogether.server.users.service.TransactionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
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
    private final TransactionApplicaion transactionApplicaion;

    @GetMapping
    @Operation(summary = "사용자 거래 내역 리스트", description = "사용자 거래 내역 리스트를 출력.")
    public ResponseEntity<TransactionList.Response> cashList(
        @Parameter(hidden = true) @AuthenticationPrincipal User user) {

        return ResponseEntity.ok(
            new TransactionList.Response(
                transactionService.userCashList(user.getEmail())
                , SUCCESS_TRANSACTION_LIST.getMessage())
        );
    }

    @PostMapping("/charge")
    @Operation(summary = "사용자 캐쉬 충전", description = "일정 금액에 대한 충전 요청.")
    public ResponseEntity<TransactionCharge.Response> cashCharge(
        @Validated @RequestBody TransactionCharge.Request request) {

        TransactionDto transactionDto = transactionService.userCashCharge(
            request.getEmail(), request.getPassword(), request.getAmount());

        return ResponseEntity.ok(
            new TransactionCharge.Response(
                transactionDto.getNickname()
                , transactionDto.getTransactionType()
                , transactionDto.getTransactionResultType()
                , transactionDto.getAmount()
                , transactionDto.getBalanceSnapshot()
                , transactionDto.getTraderNickname()
                , transactionDto.getTransactionDt()
                , SUCCESS_CHARGE.getMessage()
            )
        );
    }

    @PostMapping("/withdraw")
    @Operation(summary = "사용자 캐쉬 출금", description = "사용자가 파티 참가 시 선결제.")
    public ResponseEntity<TransactionWithdraw.Response> cashWithdraw(
        @Validated @RequestBody TransactionWithdraw.Request request
        , @Parameter(hidden = true) @AuthenticationPrincipal User user) {

        TransactionDto transactionDto =
            transactionApplicaion.userCashWithdraw(
                request.getPartyId(),
                user.getNickname());

        return ResponseEntity.ok(
            new TransactionWithdraw.Response(
                transactionDto.getNickname()
                , transactionDto.getTransactionType()
                , transactionDto.getTransactionResultType()
                , transactionDto.getAmount()
                , transactionDto.getBalanceSnapshot()
                , transactionDto.getTraderNickname()
                , transactionDto.getTransactionDt()
                , SUCCESS_PREPAYMENT.getMessage()
            )
        );
    }

    @PostMapping("/withdraw/cancel")
    @Operation(summary = "사용자 캐쉬 출금 취소", description = "사용자가 파티 탈퇴 시 선결제 취소.")
    public ResponseEntity<TransactionWithdraw.Response> cashWithdrawCancel(
        @Validated @RequestBody TransactionWithdraw.Request request
        , @Parameter(hidden = true) @AuthenticationPrincipal User user) {

        TransactionDto transactionDto =
            transactionApplicaion.userCashWithdrawCancel(
                 request.getPartyId(),
                user.getNickname());

        return ResponseEntity.ok(
            new TransactionWithdraw.Response(
                transactionDto.getNickname()
                , transactionDto.getTransactionType()
                , transactionDto.getTransactionResultType()
                , transactionDto.getAmount()
                , transactionDto.getBalanceSnapshot()
                , transactionDto.getTraderNickname()
                , transactionDto.getTransactionDt()
                , SUCCESS_CANCEL_PREPAYMENT.getMessage()
            )
        );
    }

//    @GetMapping("/test")
//    @Operation(summary = "test", description = "test.")
//    public ResponseEntity<?> test() {
//
//        TransactionDto transactionDto =
//            transactionApplicaion.test();
//
//        return ResponseEntity.ok(null);
//    }
}
