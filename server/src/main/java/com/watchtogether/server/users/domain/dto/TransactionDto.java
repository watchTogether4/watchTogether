package com.watchtogether.server.users.domain.dto;

import com.watchtogether.server.users.domain.entitiy.Transaction;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransactionDto {

    private String email;
    private String transactionType;
    private String transactionResultType;
    private Long amount;
    private Long balanceSnapshot;
    private String traderEmail;
    private LocalDateTime transactionDt;

    public static TransactionDto fromEntity(Transaction transaction) {
        return TransactionDto.builder()
            .email(transaction.getUser().getEmail())
            .transactionType(transaction.getTransactionType())
            .transactionResultType(transaction.getTransactionResultType())
            .amount(transaction.getAmount())
            .balanceSnapshot(transaction.getBalanceSnapshot())
            .traderEmail(transaction.getTraderEmail())
            .transactionDt(transaction.getTransactionDt())
            .build();
    }


}
