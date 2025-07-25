package com.example.myfurniture.mapper;

import com.example.myfurniture.dto.response.ProductResponse;
import com.example.myfurniture.dto.resquest.ProductCreationReq;
import com.example.myfurniture.dto.resquest.ProductUpdateReq;
import com.example.myfurniture.entity.Product;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface IProductMapper {
    Product toProduct(ProductCreationReq req);

    ProductResponse toProductResponse(Product product);
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateProduct(@MappingTarget Product product, ProductUpdateReq req);
}
