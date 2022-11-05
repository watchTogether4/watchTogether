package com.watchtogether.server.users.service;

import com.watchtogether.server.users.domain.dto.TransactionDto;

public interface TransactionService {


    /**
     * 사용자 캐시 충전
     * @param email     아이디
     * @param password  비밀번호
     * @param amount    충전금액
     * @return
     */
    TransactionDto userCashCharge(String email, String password, Long amount);
}
