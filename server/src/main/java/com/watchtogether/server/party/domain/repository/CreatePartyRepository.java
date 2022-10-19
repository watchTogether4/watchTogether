package com.watchtogether.server.party.domain.repository;

import com.watchtogether.server.party.domain.entitiy.CreateParty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CreatePartyRepository extends JpaRepository<CreateParty, Long> {
}
