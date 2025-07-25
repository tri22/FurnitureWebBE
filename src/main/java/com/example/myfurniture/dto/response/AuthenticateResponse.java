package com.example.myfurniture.dto.response;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthenticateResponse {
    private String token;
    private boolean isAuthenticated;
    private String message;

}
