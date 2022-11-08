package com.watchtogether.server.party.service;

import com.watchtogether.server.party.domain.entitiy.InviteParty;
import com.watchtogether.server.party.domain.entitiy.Party;
import com.watchtogether.server.party.domain.model.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.Optional;

public interface PartyService extends UserDetailsService {
    /**
     * 파티 생성
     *
     * @param form
     * @return
     */
    Party createParty(CreatePartyForm form);


    /**
     * 파티 참가인원수 증가(addMember) 및 파티의 참가인원수 체크(addPartyMember) 후 파티 수락
     *
     * @param form
     * @return
     */
    ResponseEntity<Object> acceptParty(AcceptPartyForm form);


    /**
     * 파티 참가인원수 증가
     *
     * @param form
     * @return
     */
    Party addMember(AcceptPartyForm form);


    /**
     * findAddPartyMember함수의 리턴값을 바탕으로 파티멤버테이블에 저장
     *
     * @param partyId
     * @return
     */
    ResponseEntity<Object> savePartyMember(Long partyId);


    /**
     * invite 테이블에서 참가를 수락한 사람들을 쿼리문으로 검색
     *
     * @param partyId
     * @return
     */
    List<Object[]> findAddPartyMember(Long partyId);


    /**
     * invite 테이블에서 uuid와 사용자 닉네임으로 사용자 검색
     *
     * @param form
     * @return
     */
    InviteParty findUser(AcceptPartyForm form);


    List<String> myPartyMembers(List<Optional<Party>> list);
    List<Optional<Party>> findMyParties(FindMyPartiesForm form);

    /**
     * 파티참가
     *
     * @param form
     * @return
     */
    ResponseEntity<Object> joinParty(JoinPartyForm form);

    /**
     * 이용자에게 보여지는 파티리스트 목록
     *
     * @return
     */
    List<Party> showPartyList ();

    /**
     * 파티 탈퇴(파티가 완성되기전 (4명이 모이기전 대기상태일 때))
     *
     * @return
     */
    ResponseEntity<Object> leaveParty(LeavePartyForm form);


    // todo 파티 탈퇴 전 자신이 속한 파티 존재 여부 확인

    /**
     * 파티 탈퇴 전 자신이 속한 파티 존재 여부 확인
     * @param nickname 닉네임
     */
    void findMyPartiesBeforeDeleteUser(String nickname);

    List<SendAlertForm> sendInviteAlert(Party party, String leader);

    ResponseEntity<Object> checkInviteMessage(CheckInviteMessageForm form);

    List<SendAlertForm> changePassword(ChangePasswordForm form);

    /**
     * 파티 참가 전 partyId로 해당 Party 객체정보 가져오기
     * @param   partyId
     * @return  Party 객체 정보
     */
    Party validateAndFindPartyWithPartyIdBeforeJoin(Long partyId, String nickname);

    /**
     * 파티 참가 전 partyId로 해당 Party 객체정보 가져오기
     * @param   partyId
     * @return  Party 객체 정보
     */
    Party validateAndFindPartyWithPartyIdBeforeLeave(Long partyId, String nickname);


    /**
     * 파티제거(파티장이 나왔을때)
     *
     * @return
     */
    ResponseEntity<Object> deleteParty(Long partyId);


    // 알림 메시지는 3일안에 받지 않으면 자동으로 파티 나가기가 되고 그 파티는 모집이 다시 시작된다.

    // 파티장이 나갈경우 파티는 자동으로 해체된다.
    // 메시지를 받지 않으면(파티 탈퇴) 방 사람들에게 모두 메시지가 전송이 된다.
    // 리더에게는 추가로 비밀번호를 바꾸라는 메시지를 전송한다.
    // 리더가 비밀번호를 바꾸면 방 멤버들에게 비밀번호가 바뀌었다는 메시지를 전송한다.
    // 결제일까지 파티가 다 차지 않는다면 파티는 해체된다.
    // 만약 새로운 파티원이 들어온다면 그 결제일까지는 무료로 볼수 있다.

}
