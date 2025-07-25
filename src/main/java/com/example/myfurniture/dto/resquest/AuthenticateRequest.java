package com.example.myfurniture.dto.resquest;

import lombok.Data;

@Data
public class AuthenticateRequest {
    private String username;
    private String password;
}
