package com.adish.grahaksensemono.user.service;

import com.adish.grahaksensemono.user.Dto.UpdateUserRequestDto;
import com.adish.grahaksensemono.user.Dto.UserRequestDto;
import com.adish.grahaksensemono.user.Dto.UserResponseDto;

import java.util.List;

public interface UserService {

    UserResponseDto createUser(UserRequestDto userRequestDto);

    UserResponseDto updateUser(UpdateUserRequestDto userRequestDto);

    List<UserResponseDto> getAllUsers();

    UserResponseDto getUserByUserName(String userName);

    boolean userDeleteById(Long userId);

}
