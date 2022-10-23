package com.example.sockettest.persist;

import com.example.sockettest.persist.entity.Dialog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DialogRespository extends JpaRepository<Dialog, Long> {
    List<Dialog> findAllByPartyIdOrderByDtAsc(String partyId);
}
