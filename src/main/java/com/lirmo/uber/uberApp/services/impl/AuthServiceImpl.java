package com.lirmo.uber.uberApp.services.impl;

import java.util.Optional;
import java.util.Set;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lirmo.uber.uberApp.dto.DriverDto;
import com.lirmo.uber.uberApp.dto.LoginResponseDto;
import com.lirmo.uber.uberApp.dto.SignUpDto;
import com.lirmo.uber.uberApp.dto.UserDto;
import com.lirmo.uber.uberApp.entities.Driver;
import com.lirmo.uber.uberApp.entities.User;
import com.lirmo.uber.uberApp.enums.Role;
import com.lirmo.uber.uberApp.exceptions.ResourceNotFoundException;
import com.lirmo.uber.uberApp.exceptions.RuntimeConflictException;
import com.lirmo.uber.uberApp.repositories.UserRepository;
import com.lirmo.uber.uberApp.security.JWTService;
import com.lirmo.uber.uberApp.services.AuthService;
import com.lirmo.uber.uberApp.services.DriverService;
import com.lirmo.uber.uberApp.services.RiderService;
import com.lirmo.uber.uberApp.services.UserService;
import com.lirmo.uber.uberApp.services.WalletService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final RiderService riderService;
    private final WalletService walletService;
    private final DriverService driverService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;

    @Override
    public LoginResponseDto login(String email, String password) {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(email, password));
        User user = (User) authentication.getPrincipal();
        String accessToken = jwtService.genarateAccessToken(user);
        String refreshToken = jwtService.genarateRefreshToken(user);

        return LoginResponseDto.builder()
                .accessToken(accessToken).refreshToken(refreshToken)
                .userData(modelMapper.map(user, UserDto.class))
                .build();

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
        mappedUser.setPassword(passwordEncoder.encode(mappedUser.getPassword()));
        User savedUser = userRepository.save(mappedUser);
        riderService.createNewRider(savedUser);
        walletService.createNewWallet(savedUser);

        return modelMapper.map(savedUser, UserDto.class);
    }

    @Override
    @Transactional
    public DriverDto onboardNewDriver(Long userId, String vehicalId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User is not found with id: " + userId));

        if (user.getRoles().contains(Role.DRIVER)) {
            throw new RuntimeException("User with id: " + userId + " is already a driver");
        }
        user.getRoles().add(Role.DRIVER);
        userRepository.save(user);
        Driver driver = driverService.createNewDriver(user, vehicalId);

        return modelMapper.map(driver, DriverDto.class);
    }

    @Override
    public LoginResponseDto refreshToken(String refreshToken) {
        Long userId = jwtService.getUserIdFromToken(refreshToken);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User is not found with id: " + userId));
        String accessToken = jwtService.genarateAccessToken(user);
        refreshToken = jwtService.genarateRefreshToken(user);
        return LoginResponseDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken).userData(modelMapper.map(user, UserDto.class))
                .build();

    }

}
