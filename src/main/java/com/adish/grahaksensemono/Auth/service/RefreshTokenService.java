package com.adish.grahaksensemono.Auth.service;

import com.adish.grahaksensemono.Auth.entity.RefreshToken;

public interface RefreshTokenService {

    RefreshToken create(String userName);

    RefreshToken verify(String token);

    void deleteByToken(String refreshToken);

}
