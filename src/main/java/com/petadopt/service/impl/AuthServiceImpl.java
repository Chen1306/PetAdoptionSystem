package com.petadopt.service.impl;

import com.petadopt.common.BusinessException;
import com.petadopt.common.ResultCode;
import com.petadopt.dto.LoginRequest;
import com.petadopt.dto.RegisterRequest;
import com.petadopt.entity.User;
import com.petadopt.mapper.UserMapper;
import com.petadopt.service.AuthService;
import com.petadopt.service.TokenService;
import com.petadopt.util.PasswordUtil;
import com.petadopt.vo.LoginResponse;
import com.petadopt.vo.LoginUser;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserMapper userMapper;
    private final TokenService tokenService;

    public AuthServiceImpl(UserMapper userMapper, TokenService tokenService) {
        this.userMapper = userMapper;
        this.tokenService = tokenService;
    }

    @Override
    public Long register(RegisterRequest request) {
        if (userMapper.findByUsername(request.getUsername()) != null) {
            throw new BusinessException(ResultCode.BAD_REQUEST, "username already exists");
        }
        if (userMapper.findByPhone(request.getPhone()) != null) {
            throw new BusinessException(ResultCode.BAD_REQUEST, "phone already exists");
        }
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(PasswordUtil.md5(request.getPassword()));
        user.setPhone(request.getPhone());
        user.setRole("user");
        user.setStatus("ENABLED");
        userMapper.insert(user);
        return user.getId();
    }

    @Override
    public LoginResponse login(LoginRequest request) {
        User user = userMapper.findByUsername(request.getUsername());
        if (user == null || !StringUtils.hasText(user.getPassword())
                || !user.getPassword().equals(PasswordUtil.md5(request.getPassword()))) {
            throw new BusinessException(ResultCode.BAD_REQUEST, "username or password is incorrect");
        }
        if ("DISABLED".equalsIgnoreCase(user.getStatus())) {
            throw new BusinessException(ResultCode.FORBIDDEN, "user has been disabled");
        }
        LoginUser loginUser = new LoginUser(user.getId(), user.getUsername(), user.getRole());
        LoginResponse response = new LoginResponse();
        response.setUserId(user.getId());
        response.setUsername(user.getUsername());
        response.setRole(user.getRole());
        response.setToken(tokenService.generateToken(loginUser));
        return response;
    }
}
