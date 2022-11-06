package com.example.sockettest.alert.persist;

import com.example.sockettest.alert.persist.entity.Party;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface PartyRepository extends JpaRepository<Party, Long> {
    List<Party> findByPartyFullIsFalseAndInvisibleDtBefore(LocalDateTime now);

    Optional<Party> findById(long partyId);
}
