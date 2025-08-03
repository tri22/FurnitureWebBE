package com.example.myfurniture.repository;

import com.example.myfurniture.entity.Account;
import com.example.myfurniture.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    Account findAccountByUsername(String username);

    Account findAccountByUser_Id(Long user_id);
}
