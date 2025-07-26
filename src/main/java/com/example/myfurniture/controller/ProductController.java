package com.example.myfurniture.controller;


import com.example.myfurniture.configuration.JWTAuthenticationFilter;
import com.example.myfurniture.dto.response.ProductResponse;
import com.example.myfurniture.dto.request.ProductCreationReq;
import com.example.myfurniture.dto.request.ProductUpdateReq;
import com.example.myfurniture.entity.Product;
import com.example.myfurniture.service.LogService;
import com.example.myfurniture.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService pdService;


    @Autowired
    private LogService logService;
    @Autowired
    JWTAuthenticationFilter jwtAuthenticationFilter;

    @GetMapping
    public List<Product> getAllProducts() {
        return pdService.getAllProducts();
    }

    @GetMapping("/{id}")
    public Product getProductById(@PathVariable Long id) {
        return pdService.getProductById(id);

    }

    @PostMapping
    public ProductResponse createProduct(@RequestBody ProductCreationReq productRequest) {
        return pdService.createProduct(productRequest);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponse> updateProduct(@PathVariable Long id,
            @RequestBody ProductUpdateReq productRequest) {
        return pdService.updateProduct(id, productRequest).map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/delete/{id}")
    public String deleteProduct(@PathVariable Long id) {
        boolean isDeleted = pdService.deleteProduct(id);
        return isDeleted ? "Product deleted successfully" : "Product not found";
    }

}
