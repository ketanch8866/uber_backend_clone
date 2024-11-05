package com.lirmo.uber.uberApp.entities;

import java.util.Set;

import com.lirmo.uber.uberApp.enums.Role;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import lombok.Getter;
import lombok.Setter;
import jakarta.persistence.*;

@Entity
@Table(name = "app_user")
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Column(unique = true)
    private String email;
    private String password;
    @ElementCollection(fetch = FetchType.LAZY)
    @Enumerated(EnumType.STRING)
    private Set<Role> roles;

}
