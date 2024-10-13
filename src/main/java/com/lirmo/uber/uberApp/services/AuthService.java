package com.lirmo.uber.uberApp.services;

import com.lirmo.uber.uberApp.dto.DriverDto;
import com.lirmo.uber.uberApp.dto.SignUpDto;
import com.lirmo.uber.uberApp.dto.UserDto;

public interface AuthService {
    void login(String email, String password);

    UserDto signUp(SignUpDto signUpDto);

    DriverDto onboardNewDriver(Long userId);
}
