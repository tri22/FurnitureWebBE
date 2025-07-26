package com.example.myfurniture.repository;

import com.example.myfurniture.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUserId(Long userId);

    List<Order> findAllByOrderDateBetween(LocalDate startOfWeek, LocalDate endOfWeek);

    List<Order> findAllByOrderDateBetweenAndStatus(LocalDate startOfWeek, LocalDate endOfWeek, String cancelled);

    List<Order> getOrdersByOrderDate(LocalDate day);
}
