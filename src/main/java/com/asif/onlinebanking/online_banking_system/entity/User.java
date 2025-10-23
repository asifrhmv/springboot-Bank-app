package com.asif.onlinebanking.online_banking_system.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name="users")
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true,nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false,unique = true)
    private String email;

    private String fullName;

    @ManyToMany(fetch = FetchType.EAGER) // Çoxdan Çoxa əlaqə
    @JoinTable(
            name = "user_roles", // Aralıq (Join) cədvəlinin adı
            joinColumns = @JoinColumn(name = "user_id"), // User cədvəlinin açarı
            inverseJoinColumns = @JoinColumn(name = "role_id") // Role cədvəlinin açarı
    )
    private Set<Role> roles = new HashSet<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Account> accounts = new ArrayList<>();

}
