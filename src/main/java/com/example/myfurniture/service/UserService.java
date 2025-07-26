package com.example.myfurniture.service;

import com.example.myfurniture.dto.response.UserResponse;
import com.example.myfurniture.dto.request.UserCreationReq;
import com.example.myfurniture.dto.request.UserUpdateReq;
import com.example.myfurniture.entity.User;
import com.example.myfurniture.mapper.IUserMapper;
import com.example.myfurniture.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    IUserMapper userMapper;
    @Autowired
    UserRepository userRepository;
    public UserResponse createUser(@Valid UserCreationReq userRequest) {
        User user =userMapper.toUser(userRequest);
        return userMapper.toUserResponse(userRepository.save(user));
    }

    public List<UserResponse> getAllUsers() {
        return userRepository.findAll().stream().map(e->userMapper.toUserResponse(e)).toList();
    }

    public UserResponse updateUser(Long userId, @Valid UserUpdateReq userUpdateReq) {
        User user = userRepository.findById(userId).orElseThrow();
        userMapper.updateUser(user,userUpdateReq);
        return userMapper.toUserResponse(userRepository.save(user));
    }

    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }

    public User getCurrentUser() {
        return null;
    }
}
