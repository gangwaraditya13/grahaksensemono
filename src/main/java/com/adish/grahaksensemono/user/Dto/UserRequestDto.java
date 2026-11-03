package com.adish.grahaksensemono.user.Dto;

import com.adish.grahaksense_user.entity.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRequestDto {
    private String name;
    private String userName;
    private String password;
    private String email;
    private String fullAddress;
    private String phone;
}
