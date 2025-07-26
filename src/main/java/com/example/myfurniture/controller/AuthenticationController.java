package com.example.myfurniture.controller;

import com.example.myfurniture.dto.response.AuthenticateResponse;
import com.example.myfurniture.dto.request.AuthenticateRequest;
import com.example.myfurniture.service.AuthenticateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/authenticate")
@RestController
@Slf4j
public class AuthenticationController {
    @Autowired
    AuthenticateService authenticateService;
    @PostMapping("/login")
    public AuthenticateResponse authenticate(@RequestBody AuthenticateRequest authenticateRequest) {
        return authenticateService.authenticate(authenticateRequest);
    }
}
