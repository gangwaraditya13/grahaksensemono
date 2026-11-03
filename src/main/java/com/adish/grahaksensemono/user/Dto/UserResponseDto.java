package com.adish.grahaksensemono.user.Dto;

import com.adish.grahaksensemono.user.entity.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseDto {

    private Long id;
    private String name;
    private String userName;
    private String email;
    private String fullAddress;
    private String phone;
    private Set<Role> roles = new HashSet<>();
    private boolean status;
    private LocalDateTime createdAt;
    private LocalDateTime UpdatedAt;
}
