package com.watchtogether.server.party.domain.repository;

import com.watchtogether.server.party.domain.entitiy.Party;
import com.watchtogether.server.party.domain.entitiy.PartyMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PartyMemberRepository extends JpaRepository<PartyMember, Long> {
}
