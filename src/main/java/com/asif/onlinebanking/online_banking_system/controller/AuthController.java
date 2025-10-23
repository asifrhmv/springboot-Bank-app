package com.asif.onlinebanking.online_banking_system.controller;

import com.asif.onlinebanking.online_banking_system.dto.AuthRequest;
import com.asif.onlinebanking.online_banking_system.dto.AuthResponse;
import com.asif.onlinebanking.online_banking_system.dto.RegisterRequest;
import com.asif.onlinebanking.online_banking_system.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @PostMapping("/register")
    public AuthResponse register(@RequestBody RegisterRequest request) {
        return userService.register(request);
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody AuthRequest request) {
        return userService.login(request);
    }
}
