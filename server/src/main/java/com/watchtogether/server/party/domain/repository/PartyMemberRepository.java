package com.watchtogether.server.party.domain.repository;

import com.watchtogether.server.party.domain.entitiy.PartyMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PartyMemberRepository extends JpaRepository<PartyMember, Long> {
    List<PartyMember> findByNickName(String nickname);
}
