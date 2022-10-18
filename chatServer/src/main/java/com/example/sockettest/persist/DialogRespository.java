package com.example.sockettest.persist;

import com.example.sockettest.persist.entity.Dialog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DialogRespository extends JpaRepository<Dialog, Long> {

}
