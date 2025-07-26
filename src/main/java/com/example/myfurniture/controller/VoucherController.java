package com.example.myfurniture.controller;


import com.example.myfurniture.configuration.JWTAuthenticationFilter;
import com.example.myfurniture.dto.response.ApiResponse;
import com.example.myfurniture.dto.request.VoucherRequest;
import com.example.myfurniture.entity.Voucher;
import com.example.myfurniture.service.LogService;
import com.example.myfurniture.service.VoucherService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/vouchers")
@Slf4j
public class VoucherController {
    @Autowired
    VoucherService voucherService;


    @Autowired
    private LogService logService;
    @Autowired
    JWTAuthenticationFilter jwtAuthenticationFilter;


    @PostMapping("/add")
    public ApiResponse<Voucher> creatVoucher(@RequestBody @Valid VoucherRequest req) {
        ApiResponse<Voucher> apiResponse = new ApiResponse<>();
        apiResponse.setResult(voucherService.creatVoucher(req));
        return apiResponse;
    }

    @GetMapping("/all")
    public ApiResponse<List<Voucher>> getAllVouchers() {
        return ApiResponse.<List<Voucher>>builder()
                .result(voucherService.getAllVoucher())
                .build();

    }

    @GetMapping("/find/{code}")
    public ApiResponse<Voucher> getVoucherByCode( @PathVariable String code) {
        return ApiResponse.<Voucher>builder()
                .result(voucherService.getVoucherByCode(code))
                .build();

    }

    @PutMapping("/update/{voucherId}")
    public ApiResponse<Voucher> updateVoucher(@PathVariable("voucherId") long id, @RequestBody VoucherRequest req) {
        ApiResponse<Voucher> apiResponse = new ApiResponse<>();
        apiResponse.setResult(voucherService.updateVoucher(id, req));
        return apiResponse;
    }

    @DeleteMapping("/delete/{voucherId}")
    public void deleteVoucher(@PathVariable("voucherId") long id) {
        voucherService.deleteVoucher(id);
    }

}
