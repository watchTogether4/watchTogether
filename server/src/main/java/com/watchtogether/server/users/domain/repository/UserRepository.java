package com.watchtogether.server.users.domain.repository;

import com.watchtogether.server.users.domain.entitiy.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {

    boolean existsByNickname(String nickname);

    Optional<User> findByNickname(String nickname);

    Optional<User> findByEmailAndNickname(String email, String nickname);

    Optional<User> findByResetPasswordKey(String code);
}
