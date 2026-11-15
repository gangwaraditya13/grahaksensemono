package com.adish.grahaksensemono.Auth.service.Impl;

import com.adish.grahaksensemono.Auth.entity.RefreshToken;
import com.adish.grahaksensemono.Auth.repository.RefreshTokenRepository;
import com.adish.grahaksensemono.Auth.service.RefreshTokenService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.UUID;

@Service
@Transactional
public class RefreshTokenServiceImpl implements RefreshTokenService {

    @Value("${app.jwt.refresh-expiration-ms}")
    private long refreshExpirationMs;

    private final RefreshTokenRepository repo;

    public RefreshTokenServiceImpl(RefreshTokenRepository repo) {
        this.repo = repo;
    }

    @Override
    public RefreshToken create(String userName) {
        repo.deleteByuserName(userName);
        repo.flush();
        RefreshToken rt = new RefreshToken();
        rt.setUserName(userName);
        rt.setToken(UUID.randomUUID().toString());
        rt.setExpiryDate(Instant.now().plusMillis(refreshExpirationMs));
        return repo.save(rt);
    }

    @Override
    public RefreshToken verify(String token) {
        if (token == null || token.isBlank()) {
            throw new RuntimeException("Invalid refresh token");
        }
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
        if (refreshToken != null && !refreshToken.isBlank()) {
            repo.deleteByToken(refreshToken);
        }
    }
}
