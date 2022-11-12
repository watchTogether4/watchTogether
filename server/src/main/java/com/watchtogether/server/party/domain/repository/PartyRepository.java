package com.watchtogether.server.party.domain.repository;

import com.watchtogether.server.party.domain.entitiy.Party;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface PartyRepository extends JpaRepository<Party, Long> {
    List<Party> findByPartyFullIsFalseAndInvisibleDtBefore(LocalDateTime now);


    List<Party> findByPayDtBetween(LocalDateTime Day1, LocalDateTime Day2);

    List<Party> findByCreatedDtBefore(LocalDateTime nowMinus7Days);

    List<Party> findByLeaderNickname(String nickname);
}
