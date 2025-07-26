package com.example.myfurniture.service;

import com.example.myfurniture.entity.Cart;
import com.example.myfurniture.entity.CartItem;
import com.example.myfurniture.entity.Product;
import com.example.myfurniture.entity.User;
import com.example.myfurniture.exception.AppException;
import com.example.myfurniture.exception.ErrorCode;
import com.example.myfurniture.repository.CartItemRepository;
import com.example.myfurniture.repository.CartRepository;
import com.example.myfurniture.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;

@Service
@Transactional
public class CartService {

    @Autowired
    CartRepository cartRepository;
    @Autowired
    CartItemRepository cartItemRepository;

    @Autowired
    UserService userService;

    @Autowired
    ProductRepository productRepository;

    public void addToCart(long productId, int quantity) {
        User user = userService.getCurrentUser();
        Cart cart = cartRepository.findByUser_Id(user.getId());
        if(cart == null) {
            cart = new Cart();
            cart.setUser(user);
            cart.setItems(new HashSet<>());
        }
        // Kiểm tra xem sản phẩm đã có trong giỏ chưa
        CartItem existingItem = cart.getItems().stream()
                .filter(item -> item.getProduct().getId() == (productId))
                .findFirst()
                .orElse(null);
        if (existingItem != null) {
            existingItem.setQuantity(existingItem.getQuantity() + quantity);
        } else {
            Product product = productRepository.findById(productId)
                    .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_EXIST));
            CartItem newItem = new CartItem();
            newItem.setProduct(product);
            newItem.setQuantity(quantity);
            newItem.setCart(cart);
            cart.getItems().add(newItem);
        }
        cartRepository.save(cart);
    }

    public void clearCartForCurrentUser() {
        User user = userService.getCurrentUser();
        Cart cart = cartRepository.findByUser_Id(user.getId());
        cart.getItems().clear(); // xoá hết items
        cartRepository.save(cart);
    }

    public void removeCartItemById(long cartItemId) {
        cartItemRepository.deleteById(cartItemId);
    }

    public void updateCartItemQuantity(long cartItemId, int quantity) {
        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new AppException(ErrorCode.UNCATEGORIZED_EXCEPTION));
        cartItem.setQuantity(quantity);
        cartItemRepository.save(cartItem);
    }

    public Cart getCart() {
        User user = userService.getCurrentUser();
        Cart cart = user.getCart();
        if (cart == null) {
            System.out.println("creating new cart");
            cart = new Cart();
            cart.setUser(user);
            cart.setItems(new HashSet<CartItem>());
            cartRepository.save(cart);
        }
        return cart;
    }
}
