package com.watchtogether.server.party.service;

import com.watchtogether.server.party.domain.entitiy.InviteParty;
import com.watchtogether.server.party.domain.entitiy.Party;
import com.watchtogether.server.party.domain.model.AcceptPartyForm;
import com.watchtogether.server.party.domain.model.CreatePartyForm;
import com.watchtogether.server.party.domain.model.FindMyPartiesForm;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.Optional;

public interface PartyService extends UserDetailsService {
    /**
     * 사용자 회원가입 신청
     *
     * @param form
     * @return
     */
    Party createParty(CreatePartyForm form);
    /**
     * 사용자 회원가입 신청
     *
     * @param form
     * @return
     */
    ResponseEntity<Object> acceptParty(AcceptPartyForm form);
    /**
     * 사용자 회원가입 신청
     *
     * @param form
     * @return
     */
    Party addMember(AcceptPartyForm form);
    /**
     * 사용자 회원가입 신청
     *
     * @param form
     * @return
     */
    ResponseEntity<Object> addPartyMember(AcceptPartyForm form);
    /**
     * 사용자 회원가입 신청
     *
     * @param partyId
     * @return
     */
    ResponseEntity<Object> savePartyMember(Long partyId);
    /**
     * 사용자 회원가입 신청
     *
     * @param partyId
     * @return
     */
    List<Object[]> findAddPartyMember(Long partyId);
    /**
     * 사용자 회원가입 신청
     *
     * @param form
     * @return
     */
    InviteParty findUser(AcceptPartyForm form);
    List<String> myPartyMembers(List<Optional<Party>> list);
    List<Optional<Party>> findMyParties(FindMyPartiesForm form);
}
