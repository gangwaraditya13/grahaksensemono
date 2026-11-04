package com.adish.grahaksensemono.user.repository;

import com.adish.grahaksensemono.user.entity.OtpToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OtpRepository extends JpaRepository<OtpToken, Long> {

    Optional<OtpToken> findByUserName(String userName);
    void deleteByUserName(String userName);

}
