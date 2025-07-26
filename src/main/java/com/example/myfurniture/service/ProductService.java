package com.example.myfurniture.service;

import com.example.myfurniture.dto.request.ProductCreationReq;
import com.example.myfurniture.dto.response.ProductResponse;
import com.example.myfurniture.dto.request.ProductUpdateReq;
import com.example.myfurniture.entity.Product;
import com.example.myfurniture.mapper.IProductMapper;
import com.example.myfurniture.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private IProductMapper productMapper;

    public List<Product> getAllProducts() {
        return productRepository.findByDeletedFalse();
    }

    public Product getProductById(Long id) {
        Optional<Product> product= productRepository.findById(id);
        return product.orElse(null);
    }

    public ProductResponse createProduct(ProductCreationReq productRequest) {
        Product product = productMapper.toProduct(productRequest);
        return productMapper.toProductResponse(productRepository.save(product));
    }

    public Optional<ProductResponse> updateProduct(Long id, ProductUpdateReq productRequest) {
        return productRepository.findById(id).map(product -> {
            productMapper.updateProduct(product, productRequest);
            return productMapper.toProductResponse(productRepository.save(product));
        });
    }

    public boolean deleteProduct(Long id) {
        try {
            Product product = productRepository.findById(id).orElseThrow();
            product.setDeleted(true);
            productRepository.save(product);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
