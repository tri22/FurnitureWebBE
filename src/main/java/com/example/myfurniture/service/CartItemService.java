package com.example.myfurniture.service;

import com.example.myfurniture.entity.CartItem;
import com.example.myfurniture.repository.CartItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartItemService {

    @Autowired
    CartItemRepository cartItemRepository;

    public void deleteItem(CartItem item) {
        cartItemRepository.delete(item);
    }
}
