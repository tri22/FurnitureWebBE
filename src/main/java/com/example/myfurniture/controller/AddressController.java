package com.example.myfurniture.controller;

import com.example.myfurniture.dto.request.AddressCreationReq;
import com.example.myfurniture.dto.request.AddressUpdateReq;
import com.example.myfurniture.dto.response.AddressResponse;
import com.example.myfurniture.dto.response.ApiResponse;
import com.example.myfurniture.dto.response.UserResponse;
import com.example.myfurniture.service.AddressService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/address")
@RestController
@Slf4j
public class AddressController {
    @Autowired
    AddressService addressService;

    @PostMapping("/add/{userId}")
    public ApiResponse<?> updateUser(@PathVariable Long userId, @RequestBody AddressCreationReq req){
        addressService.addNewAddress(userId,req);
        return ApiResponse.builder()
                .code(HttpStatus.OK.value())
                .message("Cập nhật thành công")
                .build();
    }
    @PutMapping("/update/{addressId}")
    public ApiResponse<AddressResponse> updateUser(@PathVariable Long addressId, @RequestBody AddressUpdateReq req){
        return ApiResponse.<AddressResponse>builder()
                .code(HttpStatus.OK.value())
                .result(addressService.updateAddress(addressId,req))
                .message("Cập nhật thành công")
                .build();
    }

    @DeleteMapping("/delete/{addressId}")
    public ApiResponse<?> deleteUser(@PathVariable Long addressId){
        try{
            addressService.deleteAddress(addressId);
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
