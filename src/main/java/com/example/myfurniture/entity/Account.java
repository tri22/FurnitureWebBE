package com.example.myfurniture.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "accounts")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Account {
    @Id
    private String username;
    private String password;
    @OneToOne(mappedBy = "account", cascade = CascadeType.ALL, orphanRemoval = true)
    private User user;
    @ManyToOne
    @JoinColumn(name = "role_name")
    private Role role;
}
