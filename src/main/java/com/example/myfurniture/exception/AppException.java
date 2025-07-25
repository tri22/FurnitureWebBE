package com.example.myfurniture.exception;

import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AppException extends RuntimeException {
    ErrorCode errorCode;
}
