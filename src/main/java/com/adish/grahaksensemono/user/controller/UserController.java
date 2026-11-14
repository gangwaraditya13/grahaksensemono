package com.adish.grahaksensemono.user.controller;

import com.adish.grahaksensemono.product.service.Impl.S3FileService;
import com.adish.grahaksensemono.user.Dto.UpdateUserRequestDto;
import com.adish.grahaksensemono.user.Dto.UserDeleteRequestDto;
import com.adish.grahaksensemono.user.Dto.UserRequestDto;
import com.adish.grahaksensemono.user.Dto.UserResponseDto;
import com.adish.grahaksensemono.user.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    private UserService userService;

    private S3FileService s3FileService;

    public UserController(UserService userService, S3FileService s3FileService) {
        this.userService = userService;
        this.s3FileService = s3FileService;
    }

    /// signup user

    @PostMapping("/signup")
    public ResponseEntity<UserResponseDto> createUser(@RequestBody UserRequestDto userRequestDto){
        UserResponseDto user = userService.createUser(userRequestDto);
        if(user != null){
            return new ResponseEntity<>(user, HttpStatus.CREATED);
        }else{
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/update")
    public ResponseEntity<UserResponseDto> updateUser(@RequestBody UpdateUserRequestDto updateUserRequestDto){
        UserResponseDto userResponseDto = userService.updateUser(updateUserRequestDto);
        if (userResponseDto != null){
            return new ResponseEntity<>(userResponseDto, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{userName}")
    public ResponseEntity<UserResponseDto> getUserByUserName(@PathVariable String userName){
        UserResponseDto userByUserName = userService.getUserByUserName(userName);
        if(userByUserName != null){
            return new ResponseEntity<>(userByUserName, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /// admin api
    @GetMapping("/all/users")
    public ResponseEntity<List<UserResponseDto>> getAllUsers(){
        List<UserResponseDto> allUsers = userService.getAllUsers();
        return new ResponseEntity<>(allUsers, HttpStatus.OK);
    }

    @PostMapping("/delete")
    public ResponseEntity<?> deleteUser(@RequestBody UserDeleteRequestDto userDeleteRequestDto){

        boolean isDelete = userService.userDeleteById(userDeleteRequestDto);
        if(isDelete){
            return new ResponseEntity(HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }


    // READ: key -> public CloudFront URL
    @GetMapping("/url")
    public ResponseEntity<Map<String, String>> getUrl(@RequestParam String key) {
        return ResponseEntity.ok(Map.of("url", s3FileService.getUrl(key)));
    }

}
