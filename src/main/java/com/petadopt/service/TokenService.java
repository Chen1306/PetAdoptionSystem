package com.petadopt.service;

import com.petadopt.vo.LoginUser;

public interface TokenService {

    String generateToken(LoginUser loginUser);

    LoginUser parseToken(String token);
}
