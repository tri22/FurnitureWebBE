package com.example.myfurniture.controller;

import com.example.myfurniture.dto.response.ApiResponse;
import com.example.myfurniture.dto.response.UserResponse;
import com.example.myfurniture.dto.resquest.UserCreationReq;
import com.example.myfurniture.dto.resquest.UserUpdateReq;
import com.example.myfurniture.service.UserService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static java.util.stream.Stream.builder;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {
    @Autowired
    private UserService userService;
    @PostMapping("/create")
    public ApiResponse<UserResponse> createUser(@RequestBody @Valid UserCreationReq userRequest){
        return ApiResponse.<UserResponse>builder()
                .result(userService.createUser(userRequest))
                .build();
    }

    @GetMapping("/all")
    public List<UserResponse> getAllUsers(){
        return userService.getAllUsers();
    }

    @PutMapping("/update/{userId}")
    public ApiResponse<UserResponse> updateUser(@PathVariable Long userId, @RequestBody @Valid UserUpdateReq userUpdateReq){
        return ApiResponse.<UserResponse>builder()
                .result(userService.updateUser(userId,userUpdateReq))
                .build();
    }

    @DeleteMapping("/delete/{userId}")
    public ApiResponse<?> deleteUser(@PathVariable Long userId){
        try{
            userService.deleteUser(userId);
            return ApiResponse.<UserResponse>builder()
                    .message("User deleted")
                    .build();
        }catch(Exception e){
            return ApiResponse.<UserResponse>builder()
                    .message(e.getMessage())
                    .build();
        }
    }
}
