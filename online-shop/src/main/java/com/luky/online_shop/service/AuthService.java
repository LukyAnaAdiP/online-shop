package com.luky.online_shop.service;

import com.luky.online_shop.dto.request.AuthRequest;
import com.luky.online_shop.dto.response.LoginResponse;
import com.luky.online_shop.dto.response.RegisterResponse;

public interface AuthService {
    RegisterResponse register(AuthRequest request);
    RegisterResponse registerAdmin(AuthRequest request);
    LoginResponse login(AuthRequest request);
}
