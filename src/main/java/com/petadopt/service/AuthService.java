package com.petadopt.service;

import com.petadopt.dto.LoginRequest;
import com.petadopt.dto.RegisterRequest;
import com.petadopt.vo.LoginResponse;

public interface AuthService {

    Long register(RegisterRequest request);

    LoginResponse login(LoginRequest request);
}
