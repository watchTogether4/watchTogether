package com.watchtogether.server.users.domain.dto;

import com.watchtogether.server.users.domain.entitiy.Transaction;
import com.watchtogether.server.users.domain.type.TransactionResultType;
import com.watchtogether.server.users.domain.type.TransactionType;
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
    private TransactionType transactionType;
    private TransactionResultType transactionResultType;
    private Long amount;
    private Long balanceSnapshot;
    private String traderNickname;
    private LocalDateTime transactionDt;

    public static TransactionDto fromEntity(Transaction transaction) {
        return TransactionDto.builder()
            .email(transaction.getUser().getEmail())
            .transactionType(transaction.getTransactionType())
            .transactionResultType(transaction.getTransactionResultType())
            .amount(transaction.getAmount())
            .balanceSnapshot(transaction.getBalanceSnapshot())
            .traderNickname(transaction.getTraderNickname())
            .transactionDt(transaction.getTransactionDt())
            .build();
    }

}
