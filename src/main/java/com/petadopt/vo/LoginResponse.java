package com.petadopt.vo;

import lombok.Data;
/*
视图对象：用于把后端的数值返回前端的‘箱子’
*/
@Data
public class LoginResponse {

    private Long userId;
    private String username;
    private String role;
    private String token;
}
