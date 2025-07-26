package com.example.myfurniture.controller;


import com.example.myfurniture.configuration.JWTAuthenticationFilter;
import com.example.myfurniture.dto.request.OrderItemRequest;

import com.example.myfurniture.dto.response.ApiResponse;
import com.example.myfurniture.dto.response.OrderResponse;
import com.example.myfurniture.dto.request.OrderCreationReq;
import com.example.myfurniture.dto.request.OrderUpdateReq;
import com.example.myfurniture.entity.Product;
import com.example.myfurniture.service.LogService;
import com.example.myfurniture.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/order")
@Slf4j
public class OrderController {
    @Autowired
    private OrderService orderService;

    @Autowired
    private LogService logService;
    @Autowired
    JWTAuthenticationFilter jwtAuthenticationFilter;


    @PostMapping("/create")
    public OrderResponse createOrder(@RequestBody OrderCreationReq orderRequest) {
        return orderService.createOrder(orderRequest);
    }



    @GetMapping("/{orderId}")
    public ApiResponse<OrderResponse> getOrderById(@PathVariable Long orderId) {
        ApiResponse<OrderResponse> apiResponse = new ApiResponse<>();
        apiResponse.setResult(orderService.getOrderById(orderId));
        return apiResponse;
    }

    @GetMapping("/get-order/{userId}")
    public ApiResponse<List<OrderResponse>> getOrdersByUserId(@PathVariable Long userId) {
        ApiResponse<List<OrderResponse>> apiResponse = new ApiResponse<>();
        apiResponse.setResult(orderService.getOrdersByUserId(userId));
        return apiResponse;
    }

    @DeleteMapping("/delete/{orderId}")
    public void deleteOrder(@PathVariable Long orderId) {
        orderService.cancelOrder(orderId);
    }

    @GetMapping("/all")
    public ApiResponse<List<OrderResponse>> getAllOrder() {
        ApiResponse<List<OrderResponse>> apiResponse = new ApiResponse<>();
        apiResponse.setResult(orderService.getAllOrder());
        return apiResponse;
    }

    @PutMapping("/update/{orderId}")
    public ApiResponse<OrderResponse> updateOrder(@PathVariable Long orderId, @RequestBody OrderUpdateReq request) {
        ApiResponse<OrderResponse> apiResponse = new ApiResponse<>();
        apiResponse.setResult(orderService.updateOrder(orderId, request));
        return apiResponse;
    }

    @GetMapping("/week-best-selling/{date}")
    public ApiResponse<Product> getWeekBestSelling(@PathVariable LocalDate date) {
        ApiResponse<Product> apiResponse = new ApiResponse<>();
        apiResponse.setResult(orderService.getWeekBestSelling(date));
        return apiResponse;
    }

    @GetMapping("/week-total/{date}")
    public ApiResponse<Integer> getWeekTotal(@PathVariable LocalDate date) {
        ApiResponse<Integer> apiResponse = new ApiResponse<>();
        apiResponse.setResult(orderService.getWeekTotal(date));
        return apiResponse;
    }

    @GetMapping("/week-sale/{date}")
    public ApiResponse<Double> getWeekSale(@PathVariable LocalDate date) {
        ApiResponse<Double> apiResponse = new ApiResponse<>();
        apiResponse.setResult(orderService.getWeekSale(date));
        return apiResponse;
    }

    @GetMapping("/week-cancelled/{date}")
    public ApiResponse<Integer> getWeekCancelledOrder(@PathVariable LocalDate date) {
        ApiResponse<Integer> apiResponse = new ApiResponse<>();
        apiResponse.setResult(orderService.getWeekCancelledOrder(date));
        return apiResponse;
    }

    @GetMapping("/revenue/weekly/{date}")
    public ApiResponse<List<OrderService.SaleDataPoint>> getWeeklyRevenue(@PathVariable LocalDate date) {
        ApiResponse<List<OrderService.SaleDataPoint>> apiResponse = new ApiResponse<>();
        apiResponse.setResult(orderService.getWeeklyRevenue(date));
        return apiResponse;
    }

    @GetMapping("/revenue/monthly/{date}")
    public ApiResponse<List<OrderService.SaleDataPoint>> getMonthlyRevenue(@PathVariable LocalDate date) {
        ApiResponse<List<OrderService.SaleDataPoint>> apiResponse = new ApiResponse<>();
        apiResponse.setResult(orderService.getMonthlyRevenue(date));
        return apiResponse;
    }

    @GetMapping("/revenue/yearly/{date}")
    public ApiResponse<List<OrderService.SaleDataPoint>> getYearlyRevenue(@PathVariable LocalDate date) {
        ApiResponse<List<OrderService.SaleDataPoint>> apiResponse = new ApiResponse<>();
        apiResponse.setResult(orderService.getYearlyRevenue(date));
        return apiResponse;
    }

}
