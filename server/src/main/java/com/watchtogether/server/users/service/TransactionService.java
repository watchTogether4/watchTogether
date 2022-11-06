package com.watchtogether.server.users.service;

import com.watchtogether.server.users.domain.dto.TransactionDto;
import java.util.List;

public interface TransactionService {


    /**
     * 사용자 캐시 충전
     *
     * @param email    아이디
     * @param password 비밀번호
     * @param amount   충전금액
     * @return TransactionDto
     */
    TransactionDto userCashCharge(String email, String password, Long amount);

    /**
     * 사용자 거래 내역 출력
     *
     * @param email 아이디
     * @return TransactionDtoList
     */
    List<TransactionDto> userCashList(String email);

    /**
     * 사용자 파티 참가 시 선결제
     * @param leaderEmail       파티장 아이디
     * @param email             사용자 아이디
     * @param commissionMember  파티원 수수료
     * @param fee               전체 이용료 / 4
     * @return
     */
    TransactionDto userCashWithdraw(
        String leaderEmail,
        String email,
        int commissionMember,
        Long fee);

    /**
     * 사용자 파티 탈퇴 시 선결제 취소
     * @param leaderEmail       파티장 아이디
     * @param email             사용자 아이디
     * @param commissionMember  파티원 수수료
     * @param fee               전체 이용료 / 4
     * @return
     */
    TransactionDto userCashWithdrawCancel(
        String leaderEmail,
        String email,
        int commissionMember,
        Long fee);
}
