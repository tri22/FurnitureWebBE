package com.example.myfurniture.dto.request;

import com.example.myfurniture.entity.User;
import lombok.Data;

import java.util.Date;

@Data
public class OrderUpdateReq {
    private User user;
    private String address;
    private Date creatAt;
    private double price;
    private int quantity;
    private String status;
    String paymentMethod;
    String note;
}
