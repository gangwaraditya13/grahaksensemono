package com.adish.grahaksensemono.user.service.Impl;

import com.adish.grahaksensemono.seller.Dto.SellerDeleteRequestDto;
import com.adish.grahaksensemono.seller.service.SellerService;
import com.adish.grahaksensemono.user.Dto.Password.ForgotPasswordRequestDto;
import com.adish.grahaksensemono.user.Dto.Password.ResetPasswordRequestDto;
import com.adish.grahaksensemono.user.Dto.UserDeleteRequestDto;
import com.adish.grahaksensemono.user.Dto.UpdateUserRequestDto;
import com.adish.grahaksensemono.user.Dto.UserRequestDto;
import com.adish.grahaksensemono.user.Dto.UserResponseDto;
import com.adish.grahaksensemono.user.Mapper.UserMapper;
import com.adish.grahaksensemono.user.entity.OtpToken;
import com.adish.grahaksensemono.user.entity.User;
import com.adish.grahaksensemono.user.entity.enums.Role;
import com.adish.grahaksensemono.user.repository.OtpRepository;
import com.adish.grahaksensemono.user.repository.UserRepository;
import com.adish.grahaksensemono.user.service.EmailService;
import com.adish.grahaksensemono.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder PASSWORD_ENCODER;

    private UserMapper userMapper;

    private SellerService sellerService;

    private OtpRepository otpRepository;

    private EmailService emailService;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper, SellerService sellerService, OtpRepository otpRepository, EmailService emailService) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.sellerService = sellerService;
        this.otpRepository = otpRepository;
        this.emailService = emailService;
    }

    @Override
    public UserResponseDto createUser(UserRequestDto userRequestDto) {

        User user = userRepository.findByuserName(userRequestDto.getUserName()).orElse(null);
        if(user == null){
            User user1 = userMapper.userRequestToUser(userRequestDto);

            String encodePassword = PASSWORD_ENCODER.encode(userRequestDto.getPassword());
            user1.setPassword(encodePassword);

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
    public boolean userDeleteById(UserDeleteRequestDto deleteRequestDto) {
        try {
            if(userRepository.existsById(deleteRequestDto.getUserId())){
                User user = userRepository.findById(deleteRequestDto.getUserId()).orElseThrow();

                if(user.getPassword().matches(deleteRequestDto.getPassword())) {

                    boolean isSeller = user.getRoles().stream().anyMatch(role -> role.equals(Role.SELLER));
                    if (isSeller) {
                        SellerDeleteRequestDto sellerDeleteRequestDto = new SellerDeleteRequestDto();
                        sellerDeleteRequestDto.setUserId(deleteRequestDto.getUserId());
                        sellerDeleteRequestDto.setPassword(deleteRequestDto.getPassword());
                        sellerService.deleteSellerById(sellerDeleteRequestDto);
                    } else {
                        userRepository.deleteById(deleteRequestDto.getUserId());
                    }
                    return true;
                }

                return false;
            }
            return false;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }


    public void forgetPassword(ForgotPasswordRequestDto forgotPasswordRequestDto){
        User user = userRepository.findByEmail(forgotPasswordRequestDto.getEmail()).orElseThrow();

        boolean exists = userRepository.existsByEmail(forgotPasswordRequestDto.getEmail());

        if(!exists){
            throw new RuntimeException("User not found with email: " + forgotPasswordRequestDto.getEmail());
        }

        String otp = String.format("%06d", new Random().nextInt(999999));

        otpRepository.deleteByUserName(user.getUserName());

        OtpToken otpToken = new OtpToken();
        otpToken.setUserName(user.getUserName());
        otpToken.setOtp(otp);
        otpToken.setExpiryDate(LocalDateTime.now().plusMinutes(5));

        otpRepository.save(otpToken);

        emailService.sendOtpEmail(forgotPasswordRequestDto.getEmail(), otp, user.getUserName());

    }

    public void resetPassword(ResetPasswordRequestDto resetPasswordRequestDto){
        String userName = resetPasswordRequestDto.getUserName();
        String otp = resetPasswordRequestDto.getOtp();
        String newPassword = resetPasswordRequestDto.getNewPassword();

        OtpToken otpToken = otpRepository.findByUserName(userName).orElseThrow();

        if (otpToken.getExpiryDate().isBefore(LocalDateTime.now())) {
            otpRepository.delete(otpToken);
            throw new RuntimeException("OTP has expired");
        }

        if(!otpToken.getOtp().equals(resetPasswordRequestDto.getOtp())){
            throw new RuntimeException("Invalid OTP");
        }

        String encodedPassword = PASSWORD_ENCODER.encode(newPassword);

        User user = userRepository.findByuserName(userName).orElse(null);
        if(user!=null) {
            user.setPassword(encodedPassword);
            userRepository.save(user);
        }else{
            throw new RuntimeException("User not Found");
        }

        otpRepository.delete(otpToken);

    }

    // helper method
    @Override
    public User getUserByUserId(Long userId) {
        User user = userRepository.findById(userId).orElseThrow();
        return user;
    }

    @Override
    public void upgradeUserToSeller(Long userId) {
        User userByUserId = getUserByUserId(userId);
        userByUserId.getRoles().add(Role.SELLER);
        userRepository.save(userByUserId);
    }

    @Override
    public void upgradeUserToAnalyst(Long userId) {
        User userByUserId = getUserByUserId(userId);
        userByUserId.getRoles().add(Role.ANALYST);
        userRepository.save(userByUserId);
    }
}
