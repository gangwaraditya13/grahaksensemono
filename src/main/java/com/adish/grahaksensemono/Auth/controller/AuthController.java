package com.adish.grahaksensemono.Auth.controller;

import com.adish.grahaksensemono.Auth.Dto.JwtTokenResponseDto;
import com.adish.grahaksensemono.Auth.Dto.RefreshTokenRequestDto;
import com.adish.grahaksensemono.Auth.Dto.TokenResponseDto;
import com.adish.grahaksensemono.Auth.Dto.UserLoginRequestDto;
import com.adish.grahaksensemono.Auth.entity.RefreshToken;
import com.adish.grahaksensemono.Auth.service.AuthService;
import com.adish.grahaksensemono.Auth.service.RefreshTokenService;
import com.adish.grahaksensemono.seller.Dto.SellerRequestDto;
import com.adish.grahaksensemono.seller.Dto.SellerResponseDto;
import com.adish.grahaksensemono.seller.service.SellerService;
import com.adish.grahaksensemono.user.Dto.Password.ForgotPasswordRequestDto;
import com.adish.grahaksensemono.user.Dto.Password.ResetPasswordRequestDto;
import com.adish.grahaksensemono.user.Dto.UserRequestDto;
import com.adish.grahaksensemono.user.Dto.UserResponseDto;
import com.adish.grahaksensemono.user.entity.User;
import com.adish.grahaksensemono.user.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/Auth")
public class AuthController {

    private UserService userService;

    private SellerService sellerService;

    private AuthService authService;

    private RefreshTokenService refreshTokenService;


    public AuthController(UserService userService, SellerService sellerService, AuthService authService, RefreshTokenService refreshTokenService) {
        this.userService = userService;
        this.sellerService = sellerService;
        this.authService = authService;
        this.refreshTokenService = refreshTokenService;
    }

    @PostMapping("/signup")
    public ResponseEntity<UserResponseDto> createUser(@RequestBody UserRequestDto userRequestDto){
        UserResponseDto user = userService.createUser(userRequestDto);
        if(user != null){
            return new ResponseEntity<>(user, HttpStatus.CREATED);
        }else{
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /// flow: create User -> upgrade to seller(create seller)

    @PostMapping("/seller")
    public ResponseEntity<SellerResponseDto> createSeller(@RequestBody SellerRequestDto sellerRequestDto){
        SellerResponseDto seller = sellerService.createSeller(sellerRequestDto);
        if(seller != null){
            return new ResponseEntity<>(seller, HttpStatus.CREATED);
        }else{
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/fotget/password")
    public ResponseEntity<?> forgetPassword(@RequestBody ForgotPasswordRequestDto forgotPasswordRequestDto){
        userService.forgetPassword(forgotPasswordRequestDto);
        return ResponseEntity.ok("email send");
    }

    @PostMapping("/reset/password")
    public ResponseEntity<?> resetPassword(@RequestBody ResetPasswordRequestDto resetPasswordRequestDto){
        userService.resetPassword(resetPasswordRequestDto);
        return ResponseEntity.ok("successful");
    }

    @PostMapping("/login")
    public ResponseEntity<TokenResponseDto> login(
            @RequestBody UserLoginRequestDto userLoginRequestDto){

        return ResponseEntity.ok(authService.loginToken(userLoginRequestDto));
    }

    @PostMapping("/auth/refresh")
    public ResponseEntity<?> refresh(@RequestBody RefreshTokenRequestDto refreshTokenRequestDto) {
        String newJwt = authService.refresh(refreshTokenRequestDto);
        if(newJwt != null){
            JwtTokenResponseDto jwtTokenResponseDto = new JwtTokenResponseDto();
            jwtTokenResponseDto.setJwtToken(newJwt);
            return ResponseEntity.ok(jwtTokenResponseDto);
        }else{
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

    }

    @PostMapping("/auth/logout")
    public ResponseEntity<?> logout(@RequestBody Map<String, String> body) {
        refreshTokenService.verify(body.get("refreshToken"));
        refreshTokenService.deleteByToken(body.get("refreshToken"));
        return ResponseEntity.ok("Logged out");
    }
}
