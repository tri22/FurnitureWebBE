package com.example.myfurniture.service;

import com.example.myfurniture.configuration.JWTAuthenticationFilter;
import com.example.myfurniture.dto.request.AddressUpdateReq;
import com.example.myfurniture.dto.request.PasswordUpdateReq;
import com.example.myfurniture.dto.response.ApiResponse;
import com.example.myfurniture.dto.response.UserResponse;
import com.example.myfurniture.dto.request.UserCreationReq;
import com.example.myfurniture.dto.request.UserUpdateReq;
import com.example.myfurniture.entity.Account;
import com.example.myfurniture.entity.Address;
import com.example.myfurniture.entity.User;
import com.example.myfurniture.exception.AppException;
import com.example.myfurniture.exception.ErrorCode;
import com.example.myfurniture.mapper.IUserMapper;
import com.example.myfurniture.repository.AccountRepository;
import com.example.myfurniture.repository.AddressRepository;
import com.example.myfurniture.repository.UserRepository;
import com.nimbusds.jose.JOSEException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.text.ParseException;
import java.util.List;

@Service
public class UserService {
    @Autowired
    IUserMapper userMapper;
    @Autowired
    UserRepository userRepository;
    @Autowired
    AddressRepository addressRepository;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    UploadService uploadService;

    @Autowired
    PasswordEncoder passwordEncoder;

    private static final String UPLOAD_DIR = "public/uploads/avatars/";

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
        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userRepository.findUserByAccount_Username(username);

    }

    public UserResponse updateAvatar(Long userId, MultipartFile file) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        ApiResponse<?> response = uploadService.uploadImage(file,UPLOAD_DIR);
        if(response.getCode()==200){
            user.setAvatarUrl((String) response.getResult());
           return userMapper.toUserResponse(userRepository.save(user));
        }
        throw new AppException(ErrorCode.UPLOAD_FAILED);
    }



    public void updateUserPassword(Long userId, PasswordUpdateReq req) {
        if(!req.getNewPassword().equals(req.getConfirmPassword()))throw new RuntimeException("New password and cofirm password do not match");
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Account account = accountRepository.findAccountByUser_Id(user.getId());
        if (account == null) {
            throw new RuntimeException("Account not found for user: " + user.getFullName());
        } else if (passwordEncoder.matches(req.getOldPassword(), account.getPassword())) {
            account.setPassword(passwordEncoder.encode(req.getNewPassword()));
            accountRepository.save(account);
        }else {
            throw new RuntimeException("Mật khẩu hiện tại không chính xác!");
        }

    }
}
