package com.example.sockettest.alert.persist;

import com.example.sockettest.alert.persist.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AlertRepository extends JpaRepository<Notification, Long> {
    List<Notification> findAllByEmail(String email);

    List<Notification> findByExpiredDtLessThanEqual(LocalDateTime now);

}
