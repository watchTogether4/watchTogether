package com.watchtogether.server.users.service;

import com.watchtogether.server.party.domain.entitiy.PartyMember;
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
     *
     * @param partyId          파티 아이디
     * @param leaderNickname   파티장 닉네임
     * @param nickname         사용자 닉네임
     * @param commissionMember 파티원 수수료
     * @param fee              전체 이용료 / 4
     * @return
     */
    TransactionDto userCashWithdraw(
        Long partyId,
        String leaderNickname,
        String nickname,
        int commissionMember,
        Long fee);

    /**
     * 사용자 파티 탈퇴 시 선결제 취소
     *
     * @param partyId          파티 아이디
     * @param leaderNickname   파티장 닉네임
     * @param nickname         사용자 닉네임
     * @param commissionMember 파티원 수수료
     * @param fee              전체 이용료 / 4
     * @return
     */
    TransactionDto userCashWithdrawCancel(
        Long partyId,
        String leaderNickname,
        String nickname,
        int commissionMember,
        Long fee
    );

    /**
     * @param partyMember      파티원들
     * @param leaderNickName   파티장 닉네임
     * @param partyId          파티 아이디
     * @param commissionLeader 파티장 수수료
     * @param fee              전체 이용료 / 4
     */
    void userCashDeposit(
        List<PartyMember> partyMember,
        String leaderNickName,
        Long partyId,
        int commissionLeader,
        Long fee
    );

    /**
     * 사용자 거래 내역 삭제
     *
     * @param email 사용자 이메일
     */
    void deleteTransaction(String email);
}
