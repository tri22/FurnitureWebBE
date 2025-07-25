package com.example.myfurniture.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum ErrorCode {
    UNCATEGORIZED_EXCEPTION(9999, "Uncategorized error", HttpStatus.INTERNAL_SERVER_ERROR),
    KEY_INVALID(1001, "Invalid message key", HttpStatus.BAD_REQUEST),
    //User error code
    USER_EXISTED(1002, "User existed", HttpStatus.BAD_REQUEST),
    USER_NOT_EXISTED(1003, "User not existed!", HttpStatus.NOT_FOUND),
    USERNAME_INVALID(1004, "Username must be at least 3 characters", HttpStatus.BAD_REQUEST),
    PASSWORD_INVALID(1005, "Password must be at least 8 characters", HttpStatus.BAD_REQUEST),
    EMAIL_INVALID(1006, "Email must be correctly syntax", HttpStatus.BAD_REQUEST),
    UNAUTHENTICATED(1007, "Unauthenticated!.", HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED(1008, "You do not have permission!.", HttpStatus.FORBIDDEN),

    //Product error code
    PRODUCT_EXISTED(2001, "Product existed!.", HttpStatus.BAD_REQUEST),
    PRODUCT_NOT_EXIST(2002, "Product not exist!.", HttpStatus.BAD_REQUEST),
    INVALID_PRODUCT_NAME(2003, "Product name must be at least 3 characters", HttpStatus.BAD_REQUEST),

    //Voucher error code
    VOUCHER_EXISTED(3001, "Voucher existed!.", HttpStatus.BAD_REQUEST),
    VOUCHER_NOT_FOUND(3002, "Voucher not found!", HttpStatus.BAD_REQUEST),
    VOUCHER_OUT_OF_STOCK(3003, "Voucher out of stock!", HttpStatus.BAD_REQUEST),
    INVALID_VOUCHER_CODE(3004, "Code must longer than 4!.", HttpStatus.BAD_REQUEST),
    INVALID_VOUCHER_DISCOUNT(3005, "Discount must be at least 1% and not exceed 100%!.", HttpStatus.BAD_REQUEST),

    //Cart error code
    CART_NOT_FOUND(3001, "Cart not found!.", HttpStatus.BAD_REQUEST),
    //Payment error code
    PAYMENT_METHOD_NOT_FOUND(4011, "Payment method not found", HttpStatus.BAD_REQUEST),
    INVALID_CREDIT_CARD_NUMBER(4002, "Card number must  least 13 and not exceed 18!.", HttpStatus.BAD_REQUEST),

    //Order error code
    ORDER_NOT_FOUND(5001, "Order not found!.", HttpStatus.BAD_REQUEST),


    ;

    private int code;
    private String message;
    private HttpStatusCode statusCode;
}
