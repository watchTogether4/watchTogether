package com.watchtogether.server.party.domain.repository;

import com.watchtogether.server.party.domain.entitiy.InviteParty;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InvitePartyRepository  extends JpaRepository<InviteParty, Long> {
    Optional<InviteParty> findByReceiverNickNameAndReceiverUUID(String nick, String code);
}
