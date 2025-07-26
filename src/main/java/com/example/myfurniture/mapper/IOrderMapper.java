package com.example.myfurniture.mapper;

import com.example.myfurniture.dto.request.OrderCreationReq;
import com.example.myfurniture.dto.response.OrderResponse;
import com.example.myfurniture.dto.request.OrderUpdateReq;
import com.example.myfurniture.entity.Order;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface IOrderMapper {
    Order toOrder(OrderCreationReq order);
    OrderResponse toOrderResponse(Order order);
    void updateOrder(@MappingTarget Order order , OrderUpdateReq orderUpdateReq);
}
