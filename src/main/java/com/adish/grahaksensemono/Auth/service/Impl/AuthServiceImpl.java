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

    private AuthenticationManager authenticationManager;

    private JwtUtil jwtUtil;

    private UserDetailServiceImpl userDetailServiceImpl;

    private RefreshTokenService refreshTokenService;

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
    public String refresh(RefreshTokenRequestDto refreshTokenRequestDto){

        RefreshToken refreshToken = refreshTokenService.verify(refreshTokenRequestDto.getRefreshToken());
        String userName = refreshToken.getUserName();
        String newAccess = jwtUtil.generateAccessToken(userName);

        return newAccess;
    }


}
