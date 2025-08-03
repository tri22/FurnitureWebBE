package com.example.myfurniture.controller;
import com.example.myfurniture.dto.request.PasswordUpdateReq;
import com.example.myfurniture.entity.Account;
import com.example.myfurniture.entity.Address;
import org.springframework.web.multipart.MultipartFile;
import com.example.myfurniture.dto.response.ApiResponse;
import com.example.myfurniture.dto.response.UserResponse;
import com.example.myfurniture.dto.request.UserCreationReq;
import com.example.myfurniture.dto.request.UserUpdateReq;
import com.example.myfurniture.entity.User;
import com.example.myfurniture.service.UserService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


import java.util.List;


@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {
    @Autowired
    private UserService userService;

     // thư mục lưu file

    @PostMapping("/update-avatar/{userId}")
    public ApiResponse<?> uploadAvatar(@PathVariable Long userId,@RequestParam("image") MultipartFile file) {
        UserResponse response = userService.updateAvatar(userId,file);
        return ApiResponse.builder()
                .code(HttpStatus.OK.value())
                .message("Cập nhật thành công")
                .result(response)
                .build();
    }


    @PostMapping("/create")
    public ApiResponse<UserResponse> createUser(@RequestBody @Valid UserCreationReq userRequest){
        return ApiResponse.<UserResponse>builder()
                .result(userService.createUser(userRequest))
                .build();
    }

    @GetMapping("/all")
    public ApiResponse<List<UserResponse>> getAllUsers(){
        List<UserResponse> res = userService.getAllUsers();
        return ApiResponse.<List<UserResponse>>builder()
                .code(HttpStatus.OK.value())
                .result(res)
                .build();
    }

    @PutMapping("/update/{userId}")
    public ApiResponse<UserResponse> updateUser(@PathVariable Long userId, @RequestBody @Valid UserUpdateReq userUpdateReq){
        return ApiResponse.<UserResponse>builder()
                .code(HttpStatus.OK.value())
                .message("Cập nhật thành công")
                .result(userService.updateUser(userId,userUpdateReq))
                .build();
    }



    @PutMapping("/update-password/{userId}")
    public ApiResponse<?> updateUser(@PathVariable Long userId,@RequestBody PasswordUpdateReq req){
        userService.updateUserPassword(userId,req);
        return ApiResponse.builder()
                .code(HttpStatus.OK.value())
                .message("Cập nhật thành công")
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

    @GetMapping("/me")
    public ApiResponse<User> getCurrentUser() {
        ApiResponse<User> apiResponse = new ApiResponse<>();
        apiResponse.setResult(userService.getCurrentUser());
        return apiResponse;
    }
}
