package com.example.myfurniture.service;

import com.example.myfurniture.entity.Order;
import com.example.myfurniture.entity.OrderItem;
import com.example.myfurniture.entity.Product;
import com.example.myfurniture.entity.StockIn;
import com.example.myfurniture.repository.OrderRepository;
import com.example.myfurniture.repository.StockInRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class StockInService {
    @Autowired
    StockInRepository stockInRepository;

    @Autowired
    OrderRepository orderRepository;

    public List<StockIn> getAllRecord() {
        return stockInRepository.findAll();
    }

    public Map<Long, Product> getRemain() {
        List<StockIn> stockIns = stockInRepository.findAll();
        Map<Long, Product> remains = new HashMap<>();

        // Tính tổng nhập kho
        for (StockIn stockIn : stockIns) {
            Product original = stockIn.getProduct();
            Long id = original.getId();

            Product product = remains.get(id);
            if (product == null) {
                // Tạo bản sao để tránh thay đổi đối tượng gốc
                product = new Product();
                product.setId(original.getId());
                product.setName(original.getName());
                product.setImage(original.getImage());
//                product.setStock(0);
            }

//            product.setStock(product.getStock() + stockIn.getQuantity());
            remains.put(id, product);
        }

        // Trừ số lượng đã bán
        List<Order> orders = orderRepository.findAll();
//        for (Order order : orders) {
//            for (OrderItem detail : order.getItems()) {
//                Long id = detail.getProduct().getId();
//                Product product = remains.get(id);
//
//                if (product != null) {
//                    product.setStock(product.getStock() - detail.getQuantity());
//                } else {
//                    // Nếu sản phẩm có trong đơn hàng nhưng chưa nhập kho
//                    Product newProduct = new Product();
//                    Product original = detail.getProduct();
//
//                    newProduct.setId(original.getId());
//                    newProduct.setName(original.getName());
//                    newProduct.setImage(original.getImage());
//                    newProduct.setStock(-detail.getQuantity());
//
//                    remains.put(id, newProduct);
//                }
//            }
//        }

        return remains;
    }

}
