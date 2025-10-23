package com.asif.onlinebanking.online_banking_system.service;

import com.asif.onlinebanking.online_banking_system.dto.AuthRequest;
import com.asif.onlinebanking.online_banking_system.dto.AuthResponse;
import com.asif.onlinebanking.online_banking_system.dto.RegisterRequest;

public interface UserService {
AuthResponse register(RegisterRequest request);
AuthResponse login(AuthRequest request);
}
