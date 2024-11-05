package com.lirmo.uber.uberApp.services.impl;

import java.util.Optional;
import java.util.Set;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lirmo.uber.uberApp.dto.DriverDto;
import com.lirmo.uber.uberApp.dto.SignUpDto;
import com.lirmo.uber.uberApp.dto.UserDto;
import com.lirmo.uber.uberApp.entities.User;
import com.lirmo.uber.uberApp.enums.Role;
import com.lirmo.uber.uberApp.exceptions.RuntimeConflictException;
import com.lirmo.uber.uberApp.repositories.UserRepository;
import com.lirmo.uber.uberApp.services.AuthService;
import com.lirmo.uber.uberApp.services.RiderService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final RiderService riderService;


    @Override
    public void login(String email, String password) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'login'");
    }

    @Override
    @Transactional
    public UserDto signUp(SignUpDto signUpDto) {
        Optional<User> user = userRepository.findByEmail(signUpDto.getEmail());
    
        if (user.isPresent()) {
            throw new RuntimeConflictException("User is already exist with email " + signUpDto.getEmail());
        }
        User mappedUser = modelMapper.map(signUpDto, User.class);
        mappedUser.setRoles(Set.of(Role.RIDER));
        User savedUser = userRepository.save(mappedUser);
        riderService .createNewRider(savedUser);
        // TODO add wallet releted service here

        return modelMapper.map(savedUser, UserDto.class);
    }

    @Override
    public DriverDto onboardNewDriver(Long userId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'onboardNewDriver'");
    }

}
