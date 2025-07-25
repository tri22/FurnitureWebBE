package com.example.myfurniture.service;

import com.example.myfurniture.dto.response.AuthenticateResponse;
import com.example.myfurniture.dto.resquest.AuthenticateRequest;
import com.example.myfurniture.entity.Account;
import com.example.myfurniture.entity.User;
import com.example.myfurniture.repository.AccountRepository;
import com.example.myfurniture.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.beans.Encoder;

@Service
public class AuthenticateService {
    private AccountRepository accountRepository;
    private JwtService jwtService;
    private UserRepository userRepository;

    public AuthenticateResponse authenticate(AuthenticateRequest authenticateRequest) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        Account account = accountRepository.findAccountByUsername(authenticateRequest.getUsername());
        if (account == null) {
            return new AuthenticateResponse(null,false,"User not found");
        } else if (passwordEncoder.matches(authenticateRequest.getPassword(), account.getPassword())) {
            String token = jwtService.genToken(account);
            return  new AuthenticateResponse(token,true,"Login successful!");
        }
        return  new AuthenticateResponse(null,false,"Username or password does not match");
    }
}
