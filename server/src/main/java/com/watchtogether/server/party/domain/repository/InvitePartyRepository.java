package com.watchtogether.server.party.domain.repository;

import com.watchtogether.server.party.domain.entitiy.CreateParty;
import com.watchtogether.server.party.domain.entitiy.InviteParty;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvitePartyRepository  extends JpaRepository<InviteParty, Long> {
}
