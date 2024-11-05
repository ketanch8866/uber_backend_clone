package com.lirmo.uber.uberApp.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lirmo.uber.uberApp.dto.SignUpDto;
import com.lirmo.uber.uberApp.dto.UserDto;
import com.lirmo.uber.uberApp.services.AuthService;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;

    @PostMapping("/createUser")
    public ResponseEntity<UserDto> createUser(@RequestBody SignUpDto signUpDto) {

        return ResponseEntity.ok(authService.signUp(signUpDto));
    }

}
