package com.watchtogether.server.ott.domain.repository;

import com.watchtogether.server.ott.domain.entity.Ott;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OttRepository extends JpaRepository<Ott, Long> {

}
