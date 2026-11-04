package com.adish.grahaksensemono.user.repository;

import com.adish.grahaksensemono.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByuserName(String userName);

    boolean existsByuserName(String userName);

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);
}