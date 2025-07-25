package com.example.myfurniture.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Table(name = "roles")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Role {
    @Id
    private String name;
    private String description;
    @ManyToMany(mappedBy = "roles")
    private Set<Account> accounts;

    @ManyToMany
    @JoinTable(
            name = "role_permissions",
            joinColumns = @JoinColumn(name = "role_name"), // name = @Id của Role
            inverseJoinColumns = @JoinColumn(name = "permission_name") // name = @Id của Permission
    )
    private Set<Permission> permissions;
}
