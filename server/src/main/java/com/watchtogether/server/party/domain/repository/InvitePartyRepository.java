package com.watchtogether.server.party.domain.repository;

import com.watchtogether.server.party.domain.entitiy.InviteParty;
import com.watchtogether.server.party.domain.entitiy.Party;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
@Repository
public interface InvitePartyRepository  extends JpaRepository<InviteParty, Long> {
    Optional<InviteParty> findByReceiverNickNameAndReceiverUUID(String nick, String code);

    Optional<InviteParty> findByReceiverNickNameAndPartyAndAcceptIsTrue(String nick, Party party);

    List<InviteParty> findByParty(Party party);

    List<InviteParty> findByAcceptIsFalseAndLimitDtBefore(LocalDateTime now);

    List<InviteParty> findByPartyAndLeaderIsFalse(Party party);

    List<InviteParty> findByReceiverNickName(String nickname);

    Optional<InviteParty> findByReceiverNickNameAndParty(String nick, Party party);

    Optional<InviteParty> findByReceiverNickNameAndPartyAndAcceptIsFalse(String nick, Party party);

    void deleteAllByReceiverNickName(String nickname);

    void deleteAllByParty(Party party);


}
