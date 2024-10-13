package com.lirmo.uber.uberApp.services.impl;

import org.springframework.stereotype.Service;

import com.lirmo.uber.uberApp.dto.DriverDto;
import com.lirmo.uber.uberApp.dto.SignUpDto;
import com.lirmo.uber.uberApp.dto.UserDto;
import com.lirmo.uber.uberApp.services.AuthService;
@Service
public class AuthServiceImpl implements AuthService {

    @Override
    public void login(String email, String password) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'login'");
    }

    @Override
    public UserDto signUp(SignUpDto signUpDto) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'signUp'");
    }

    @Override
    public DriverDto onboardNewDriver(Long userId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'onboardNewDriver'");
    }
    
}
