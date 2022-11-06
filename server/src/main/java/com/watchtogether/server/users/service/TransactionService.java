package com.watchtogether.server.users.service;

import com.watchtogether.server.users.domain.dto.TransactionDto;
import java.util.List;

public interface TransactionService {


    /**
     * 사용자 캐시 충전
     * @param email     아이디
     * @param password  비밀번호
     * @param amount    충전금액
     * @return          TransactionDto
     */
    TransactionDto userCashCharge(String email, String password, Long amount);

    /**
     * 사용자 거래 내역 출력
     * @param email     아이디
     * @return          TransactionDtoList
     */
    List<TransactionDto> userCashList(String email);
}
