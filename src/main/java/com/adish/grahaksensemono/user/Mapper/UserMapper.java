package com.adish.grahaksensemono.user.Mapper;



import com.adish.grahaksensemono.user.Dto.UserRequestDto;
import com.adish.grahaksensemono.user.Dto.UserResponseDto;
import com.adish.grahaksensemono.user.entity.User;
import com.adish.grahaksensemono.user.entity.enums.Role;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class UserMapper {

    public static User userRequestToUser(UserRequestDto userRequestDto){

        User user = new User();

        user.setName(userRequestDto.getName());
        user.setUserName(userRequestDto.getUserName());
        user.setEmail(userRequestDto.getEmail());
        user.setFullAddress(userRequestDto.getFullAddress());
        user.setPhone(userRequestDto.getPhone());
        user.getRoles().add(Role.USER);
        user.setStatus(false);
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());

        return user;

    }

    public static UserResponseDto userToUserResponseDto(User user){

        UserResponseDto userResponseDto = new UserResponseDto();

        userResponseDto.setId(user.getId());
        userResponseDto.setName(user.getName());
        userResponseDto.setUserName(user.getUserName());
        userResponseDto.setEmail(user.getEmail());
        userResponseDto.setFullAddress(user.getFullAddress());
        userResponseDto.setPhone(user.getPhone());
        userResponseDto.setRoles(user.getRoles());
        userResponseDto.setStatus(user.isStatus());
        userResponseDto.setCreatedAt(user.getCreatedAt());
        userResponseDto.setUpdatedAt(user.getUpdatedAt());

        return userResponseDto;
    }
}
