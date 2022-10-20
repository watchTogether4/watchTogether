package com.watchtogether.server.users.domain.repository;

import com.watchtogether.server.users.domain.entitiy.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {

    boolean existsByNickname(String nickname);
}
