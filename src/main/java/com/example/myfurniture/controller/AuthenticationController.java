package com.example.myfurniture.controller;

import com.example.myfurniture.dto.response.ApiResponse;
import com.example.myfurniture.dto.response.AuthenticateResponse;
import com.example.myfurniture.dto.request.AuthenticateRequest;
import com.example.myfurniture.service.AuthenticateService;
import jakarta.servlet.http.HttpServlet;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/auth")
@RestController
@Slf4j
public class AuthenticationController {
    @Autowired
    AuthenticateService authenticateService;
    @PostMapping("/login")
    public ApiResponse<AuthenticateResponse> authenticate(@RequestBody AuthenticateRequest authenticateRequest) {
        AuthenticateResponse authenticateResponse = authenticateService.authenticate(authenticateRequest);
        return ApiResponse.<AuthenticateResponse>builder()
                .code(HttpStatus.OK.value())
                .message("Login successfully!")
                .result(authenticateResponse)
                .build();

    }
}
