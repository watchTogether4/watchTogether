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
     * 파티의 참가인원수 체크
     *
     * @param form
     * @return
     */
    ResponseEntity<Object> addPartyMember(AcceptPartyForm form);


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
     * 파티 탈퇴
     *
     * @return
     */
    ResponseEntity<Object> leaveParty(LeavePartyForm form);



    /**
     * 파티제거(모든 사용자가 나왔을 때)
     *
     * @return
     */


    // todo 파티 탈퇴 전 자신이 속한 파티 존재 여부 확인

    /**
     * 파티 탈퇴 전 자신이 속한 파티 존재 여부 확인
     * @param nickname 닉네임
     */
    void findMyPartiesBeforeDeleteUser(String nickname);

    List<SendInviteAlertForm> sendInviteAlert(Party party);

}
