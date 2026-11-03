package com.adish.grahaksensemono.user.service.Impl;

import com.adish.grahaksensemono.seller.service.SellerService;
import com.adish.grahaksensemono.user.Dto.UpdateUserRequestDto;
import com.adish.grahaksensemono.user.Dto.UserRequestDto;
import com.adish.grahaksensemono.user.Dto.UserResponseDto;
import com.adish.grahaksensemono.user.Mapper.UserMapper;
import com.adish.grahaksensemono.user.entity.User;
import com.adish.grahaksensemono.user.entity.enums.Role;
import com.adish.grahaksensemono.user.repository.UserRepository;
import com.adish.grahaksensemono.user.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    /// after spring security inject PASSWORDBECRIPT

    private UserMapper userMapper;

    private SellerService sellerService;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper, SellerService sellerService) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.sellerService = sellerService;
    }

    @Override
    public UserResponseDto createUser(UserRequestDto userRequestDto) {

        User user = userRepository.findByuserName(userRequestDto.getUserName()).orElse(null);
        if(user == null){
            User user1 = userMapper.userRequestToUser(userRequestDto);
            /// Incript
            user1.setPassword(userRequestDto.getPassword());

            userRepository.save(user1);

            return userMapper.userToUserResponseDto(user1);
        }

        return null;
    }

    @Override
    public UserResponseDto updateUser(UpdateUserRequestDto userRequestDto) {
        try {
            User user = userRepository.findByuserName(userRequestDto.getUserName()).orElseThrow();
            boolean change = false;

            if(!userRequestDto.getName().trim().equals("")
                    && userRequestDto.getName() != null
                    && !userRequestDto.getName().trim().equals(user.getName())){
                user.setName(userRequestDto.getName());
                change = true;
            }

            if(!userRequestDto.getUserName().trim().equals("")
                    && userRequestDto.getUserName() != null
                    && !userRequestDto.getUserName().trim().equals(user.getUserName())){
                user.setUserName(userRequestDto.getUserName());
                change = true;
            }

            if(!userRequestDto.getEmail().trim().equals("")
                    && userRequestDto.getEmail() != null
                    && !userRequestDto.getEmail().trim().equals(user.getEmail())){
                user.setEmail(userRequestDto.getEmail());
                change = true;
            }

            if(!userRequestDto.getFullAddress().trim().equals("")
                    && userRequestDto.getFullAddress() != null
                    && !userRequestDto.getFullAddress().trim().equals(user.getFullAddress())){
                user.setFullAddress(userRequestDto.getFullAddress());
                change = true;
            }

            if(!userRequestDto.getPhone().trim().equals("")
                    && userRequestDto.getPhone() != null
                    && !userRequestDto.getPhone().trim().equals(user.getPhone())){
                user.setPhone(userRequestDto.getPhone());
                change = true;
            }

            if(change){
                userRepository.save(user);
            }

            return userMapper.userToUserResponseDto(user);

        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<UserResponseDto> getAllUsers() {
        List<User> userList = userRepository.findAll();

        List<UserResponseDto> userResponseDtoList = userList
                .stream()
                .map(user -> userMapper.userToUserResponseDto(user))
                .collect(Collectors.toList());

        return userResponseDtoList;
    }

    @Override
    public UserResponseDto getUserByUserName(String userName) {
        if(userRepository.existsByuserName(userName)){
            User user = userRepository.findByuserName(userName).orElseThrow();
            return userMapper.userToUserResponseDto(user);
        }
        return null;
    }

    @Transactional
    @Override
    public boolean userDeleteById(Long userId) {
        try {
            if(userRepository.existsById(userId)){
                User user = userRepository.findById(userId).orElseThrow();
                boolean isSeller = user.getRoles().stream().anyMatch(role -> role.equals(Role.SELLER));
                if(isSeller){
                    sellerService.deleteSellerById(userId);
                }else{
                    userRepository.deleteById(userId);
                }
                return true;
            }
            return false;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }


    /// todo: update password email send -> otp verify -> new password
}
