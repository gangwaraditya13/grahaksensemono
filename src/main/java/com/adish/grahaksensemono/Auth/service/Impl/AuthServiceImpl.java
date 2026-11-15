package com.adish.grahaksensemono.Auth.service.Impl;

import com.adish.grahaksensemono.Auth.Dto.RefreshTokenRequestDto;
import com.adish.grahaksensemono.Auth.Dto.TokenResponseDto;
import com.adish.grahaksensemono.Auth.Dto.UserLoginRequestDto;
import com.adish.grahaksensemono.Auth.entity.RefreshToken;
import com.adish.grahaksensemono.Auth.security.JwtUtil;
import com.adish.grahaksensemono.Auth.service.AuthService;
import com.adish.grahaksensemono.Auth.service.RefreshTokenService;
import com.adish.grahaksensemono.user.service.Impl.UserDetailServiceImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UserDetailServiceImpl userDetailServiceImpl;
    private final RefreshTokenService refreshTokenService;

    public AuthServiceImpl(AuthenticationManager authenticationManager, JwtUtil jwtUtil, UserDetailServiceImpl userDetailServiceImpl, RefreshTokenService refreshTokenService) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.userDetailServiceImpl = userDetailServiceImpl;
        this.refreshTokenService = refreshTokenService;
    }

    @Override
    public TokenResponseDto loginToken(UserLoginRequestDto userLoginRequestDto) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            userLoginRequestDto.getUserName(), userLoginRequestDto.getPassword()));
            UserDetails userDetails = userDetailServiceImpl.loadUserByUsername(userLoginRequestDto.getUserName());
            String jwtToken = jwtUtil.generateAccessToken(userDetails.getUsername());
            RefreshToken refresh = refreshTokenService.create(userDetails.getUsername());
            TokenResponseDto tokenResponseDto = new TokenResponseDto();
            tokenResponseDto.setJwtToken(jwtToken);
            tokenResponseDto.setRefreshToken(refresh.getToken());
            return tokenResponseDto;

        } catch (Exception e) {
            throw new RuntimeException("Invalid email or password");
        }
    }

    @Override
    public String refresh(RefreshTokenRequestDto refreshTokenRequestDto) {
        try {
            if (refreshTokenRequestDto == null || refreshTokenRequestDto.getRefreshToken() == null) {
                return null;
            }
            RefreshToken refreshToken = refreshTokenService.verify(refreshTokenRequestDto.getRefreshToken());
            String userName = refreshToken.getUserName();
            return jwtUtil.generateAccessToken(userName);
        } catch (Exception e) {
            return null;
        }
    }

}
