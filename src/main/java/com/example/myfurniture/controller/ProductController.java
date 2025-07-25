package com.example.myfurniture.controller;

import com.example.myfurniture.dto.response.ApiResponse;
import com.example.myfurniture.dto.response.ProductResponse;
import com.example.myfurniture.dto.response.UserResponse;
import com.example.myfurniture.dto.resquest.ProductCreationReq;
import com.example.myfurniture.dto.resquest.ProductUpdateReq;
import com.example.myfurniture.service.ProductService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
@Slf4j
public class ProductController {
    @Autowired
    private ProductService productService;

    @PostMapping("/create")
    public ApiResponse<ProductResponse> createProduct (@RequestBody @Valid ProductCreationReq productCreationReq) {
        return ApiResponse.<ProductResponse>builder()
                .result(productService.createProduct(productCreationReq))
                .build();
    }
    @PostMapping("/all")
    public ApiResponse<List<ProductResponse>> getAllProduct () {
        return ApiResponse.<List<ProductResponse>>builder()
                .result(productService.getAllProduct())
                .build();
    }
    @PostMapping("/update/{productId}")
    public ApiResponse<ProductResponse> updateProduct (@RequestBody @Valid ProductUpdateReq productUpdateReq, @PathVariable long productId) {
        return ApiResponse.<ProductResponse>builder()
                .result(productService.updateProduct(productId,productUpdateReq))
                .build();
    }
    @PostMapping("/delete/{productId}")
    public ApiResponse<?> deleteProduct (@PathVariable long productId) {
        try{
            productService.deleteProduct(productId);
            return ApiResponse.<UserResponse>builder()
                    .message("Product deleted")
                    .build();
        }catch(Exception e){
            return ApiResponse.<UserResponse>builder()
                    .message(e.getMessage())
                    .build();
        }
    }
}
