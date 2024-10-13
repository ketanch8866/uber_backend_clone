package com.lirmo.uber.uberApp.dto;
import java.util.Set;

import com.lirmo.uber.uberApp.enums.Role;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private String name;
    private String email;
    private Set<Role> roles;
}
