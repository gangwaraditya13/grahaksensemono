package com.adish.grahaksensemono.Auth.service.Impl;

import com.adish.grahaksensemono.Auth.entity.RefreshToken;
import com.adish.grahaksensemono.Auth.repository.RefreshTokenRepository;
import com.adish.grahaksensemono.Auth.service.RefreshTokenService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
public class RefreshTokenServiceImpl implements RefreshTokenService {

    @Value("${app.jwt.refresh-expiration-ms}")
    private long refreshExpirationMs;

    private final RefreshTokenRepository repo;

    public RefreshTokenServiceImpl(RefreshTokenRepository repo) {
        this.repo = repo;
    }

    @Transactional
    @Override
    public RefreshToken create(String userName) {
        repo.deleteByuserName(userName);
        RefreshToken rt = new RefreshToken();
        rt.setUserName(userName);
        rt.setToken(UUID.randomUUID().toString());
        rt.setExpiryDate(Instant.now().plusMillis(refreshExpirationMs));
        return repo.save(rt);
    }

    @Override
    public RefreshToken verify(String token) {
        RefreshToken rt = repo.findByToken(token)
                .orElseThrow(() -> new RuntimeException("Invalid refresh token"));
        if (rt.getExpiryDate().isBefore(Instant.now())) {
            repo.delete(rt);
            throw new RuntimeException("Refresh token expired, please login again");
        }
        return rt;
    }

    @Override
    public void deleteByToken(String refreshToken) {
        repo.deleteByToken(refreshToken);
    }
}
