package com.adish.grahaksensemono.user.service;

import com.adish.grahaksensemono.user.Dto.Password.ForgotPasswordRequestDto;
import com.adish.grahaksensemono.user.Dto.Password.ResetPasswordRequestDto;
import com.adish.grahaksensemono.user.Dto.UserDeleteRequestDto;
import com.adish.grahaksensemono.user.Dto.UpdateUserRequestDto;
import com.adish.grahaksensemono.user.Dto.UserRequestDto;
import com.adish.grahaksensemono.user.Dto.UserResponseDto;
import com.adish.grahaksensemono.user.entity.User;

import java.util.List;

public interface UserService {

    UserResponseDto createUser(UserRequestDto userRequestDto);

    UserResponseDto updateUser(UpdateUserRequestDto userRequestDto);

    List<UserResponseDto> getAllUsers();

    UserResponseDto getUserByUserName(String userName);

    boolean userDeleteById(UserDeleteRequestDto deleteRequestDto);

    User getUserByUserId(Long userId);

    void upgradeUserToSeller(Long userId);

    void upgradeUserToAnalyst(Long userId);

    void forgetPassword(ForgotPasswordRequestDto forgotPasswordRequestDto);

    void resetPassword(ResetPasswordRequestDto resetPasswordRequestDto);
}
