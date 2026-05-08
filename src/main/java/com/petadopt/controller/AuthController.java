package com.petadopt.controller;

import com.petadopt.common.ApiResponse;
import com.petadopt.dto.LoginRequest;
import com.petadopt.dto.RegisterRequest;
import com.petadopt.service.AuthService;
import com.petadopt.vo.LoginResponse;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ApiResponse<Long> register(@Validated @RequestBody RegisterRequest request) {
        return ApiResponse.success("register success", authService.register(request));
    }

    @PostMapping("/login")
    public ApiResponse<LoginResponse> login(@Validated @RequestBody LoginRequest request) {
        return ApiResponse.success(authService.login(request));
    }

    @PostMapping("/logout")
    public ApiResponse<Void> logout() {
        return ApiResponse.success("logout success", null);
    }
}
