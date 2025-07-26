package com.example.myfurniture.repository;

import com.example.myfurniture.entity.StockIn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StockInRepository extends JpaRepository<StockIn,Long> {
}
