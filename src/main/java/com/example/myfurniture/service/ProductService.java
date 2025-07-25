package com.example.myfurniture.service;

import com.example.myfurniture.dto.response.ProductResponse;
import com.example.myfurniture.dto.resquest.ProductCreationReq;
import com.example.myfurniture.dto.resquest.ProductUpdateReq;
import com.example.myfurniture.entity.Product;
import com.example.myfurniture.exception.ErrorCode;
import com.example.myfurniture.mapper.IProductMapper;
import com.example.myfurniture.repository.ProductRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    @Autowired
    ProductRepository productRepository;
    @Autowired
    IProductMapper productMapper;

    public ProductResponse createProduct(ProductCreationReq productCreationReq) {
        Product product = productMapper.toProduct(productCreationReq);
        return productMapper.toProductResponse(productRepository.save(product));
    }

    public ProductResponse updateProduct(long productId, @Valid ProductUpdateReq productUpdateReq) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new RuntimeException(ErrorCode.PRODUCT_NOT_EXIST.getMessage()));
        productMapper.updateProduct(product,productUpdateReq);
        return productMapper.toProductResponse(productRepository.save(product));
    }

    public void deleteProduct(long productId) {
        productRepository.deleteById(productId);
    }

    public List<ProductResponse> getAllProduct() {
        return productRepository.findAll().stream().map(e ->productMapper.toProductResponse(e)).toList();
    }
}
