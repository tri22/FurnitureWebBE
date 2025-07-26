package com.example.myfurniture.repository;


import com.example.myfurniture.entity.Voucher;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VoucherRepository extends JpaRepository<Voucher, Long> {
    Voucher findDistinctByCode(String code);
}
