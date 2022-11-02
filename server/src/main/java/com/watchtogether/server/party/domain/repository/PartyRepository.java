package com.watchtogether.server.party.domain.repository;

import com.watchtogether.server.party.domain.entitiy.Party;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PartyRepository extends JpaRepository<Party, Long> {
    List<Party> findByPartyFullIsFalseAndInvisibleDtBefore(LocalDateTime now);
}
