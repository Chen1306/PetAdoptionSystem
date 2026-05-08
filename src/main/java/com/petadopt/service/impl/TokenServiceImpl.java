package com.petadopt.service.impl;

import com.petadopt.common.BusinessException;
import com.petadopt.common.ResultCode;
import com.petadopt.service.TokenService;
import com.petadopt.vo.LoginUser;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Service
public class TokenServiceImpl implements TokenService {

    @Override
    public String generateToken(LoginUser loginUser) {
        String raw = loginUser.getUserId() + ":" + loginUser.getUsername() + ":" + loginUser.getRole();
        return Base64.getEncoder().encodeToString(raw.getBytes(StandardCharsets.UTF_8));
    }

    @Override
    public LoginUser parseToken(String token) {
        try {
            String raw = new String(Base64.getDecoder().decode(token), StandardCharsets.UTF_8);
            String[] parts = raw.split(":");
            if (parts.length != 3 || !StringUtils.hasText(parts[0])) {
                throw new BusinessException(ResultCode.UNAUTHORIZED, "invalid token");
            }
            return new LoginUser(Long.valueOf(parts[0]), parts[1], parts[2]);
        } catch (IllegalArgumentException e) {
            throw new BusinessException(ResultCode.UNAUTHORIZED, "invalid token");
        }
    }
}
