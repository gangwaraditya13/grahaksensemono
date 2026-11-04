package com.adish.grahaksensemono.Auth.service;

import com.adish.grahaksensemono.Auth.Dto.RefreshTokenRequestDto;
import com.adish.grahaksensemono.Auth.Dto.TokenResponseDto;
import com.adish.grahaksensemono.Auth.Dto.UserLoginRequestDto;

public interface AuthService {

    TokenResponseDto loginToken(UserLoginRequestDto userLoginRequestDto);

    String refresh(RefreshTokenRequestDto refreshTokenRequestDto);
}
