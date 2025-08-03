package com.example.myfurniture.repository;

import com.example.myfurniture.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {
}
