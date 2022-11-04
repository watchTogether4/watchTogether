package com.watchtogether.server.party.domain.repository;

import com.watchtogether.server.party.domain.entitiy.InviteParty;
import com.watchtogether.server.party.domain.entitiy.Party;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface InvitePartyRepository  extends JpaRepository<InviteParty, Long> {
    Optional<InviteParty> findByReceiverNickNameAndReceiverUUID(String nick, String code);

    Optional<InviteParty> findByReceiverNickNameAndPartyAndAcceptIsTrue(String nick, Party party);
}
