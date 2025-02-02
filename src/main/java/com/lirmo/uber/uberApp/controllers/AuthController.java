package com.lirmo.uber.uberApp.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lirmo.uber.uberApp.dto.DriverDto;
import com.lirmo.uber.uberApp.dto.LoginRequestDto;
import com.lirmo.uber.uberApp.dto.LoginResponseDto;
import com.lirmo.uber.uberApp.dto.OnBoardDriverDto;
import com.lirmo.uber.uberApp.dto.SignUpDto;
import com.lirmo.uber.uberApp.dto.UserDto;
import com.lirmo.uber.uberApp.services.AuthService;
import com.lirmo.uber.uberApp.services.impl.RiderServiceImpl;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

import java.lang.reflect.Array;
import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;
    // Logger log = LoggerFactory.getLogger(AuthController.class);

    @PostMapping("/createUser")
    public ResponseEntity<UserDto> createUser(@RequestBody SignUpDto signUpDto) {
        return new ResponseEntity<>(authService.signUp(signUpDto), HttpStatus.CREATED);
    }

    @Secured("ROLE_ADMIN")
    @PostMapping("/onBoardNewDriver")
    public ResponseEntity<DriverDto> onBoardNewDriver(@RequestBody OnBoardDriverDto onBoardDriverDto) {
        // log.info("Onboarding new Driver >" + onBoardDriverDto.getId() +
        // onBoardDriverDto.getVehicalId().toString());
        return new ResponseEntity<>(
                authService.onboardNewDriver(onBoardDriverDto.getId(), onBoardDriverDto.getVehicalId()),
                HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto loginRequestDto,
            HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        LoginResponseDto res = authService.login(loginRequestDto.getUsername(), loginRequestDto.getPassword());

        Cookie cookie = new Cookie("token", res.getRefreshToken());
        cookie.setHttpOnly(true);
        httpServletResponse.addCookie(cookie);

        return ResponseEntity.ok(res);
    }

    @PostMapping("/refreshToken")
    public ResponseEntity<LoginResponseDto> refreshToken(HttpServletRequest request) {
        String refreshToken = Arrays.stream(request.getCookies())
                .filter(cookie -> "refreshToken".equals(cookie.getName())).findFirst().map(Cookie::getValue)
                .orElseThrow(() -> new AuthenticationServiceException("Refresh token not found in Cookie"));

        return ResponseEntity.ok(authService.refreshToken(refreshToken));

    }

}
