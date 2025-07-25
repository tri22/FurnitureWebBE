package com.example.myfurniture.configuration;

import com.example.myfurniture.entity.Account;
import com.example.myfurniture.entity.Role;
import com.example.myfurniture.entity.User;
import com.example.myfurniture.repository.AccountRepository;
import com.example.myfurniture.repository.RoleRepository;
import com.example.myfurniture.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Set;

@Configuration
@Slf4j
public class ApplicationInitConfig {

    PasswordEncoder passwordEncoder;

    public ApplicationInitConfig(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }
    @Bean
    ApplicationRunner applicationRunner(AccountRepository accountRepository, UserRepository userRepository, RoleRepository roleRepository) {
        return args -> {
            if (accountRepository.findAccountByUsername("admin") == null) {
                Role adminRole = Role.builder()
                        .name("ADMIN")
                        .build();
                Set<Role> roles = new HashSet<>();
                roles.add(adminRole);
                roleRepository.save(adminRole);
                Account account = Account.builder()
                        .username("admin")
                        .password(passwordEncoder.encode("admin"))
                        .roles(roles)
                        .build();
                User user = User.builder()
                        .account(account)

                        .build();

                userRepository.save(user);

                log.info("admin account created with default password {}", account.getPassword());
            }
        };
    }

}
