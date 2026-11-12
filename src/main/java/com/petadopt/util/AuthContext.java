package com.petadopt.util;

import com.petadopt.common.BusinessException;
import com.petadopt.common.ResultCode;
import com.petadopt.vo.LoginUser;

import javax.servlet.http.HttpServletRequest;

/*
从请求中读取登陆用户
*/

public final class AuthContext {

    private static final String ATTRIBUTE_LOGIN_USER = "loginUser";

    private AuthContext() {
    }

    public static LoginUser getLoginUser(HttpServletRequest request) {
        Object value = request.getAttribute(ATTRIBUTE_LOGIN_USER);
        if (!(value instanceof LoginUser)) {
            throw new BusinessException(ResultCode.UNAUTHORIZED, "Please login first");
        }
        return (LoginUser) value;
    }

    public static LoginUser getAdmin(HttpServletRequest request) {
        LoginUser loginUser = getLoginUser(request);
        if (!"admin".equalsIgnoreCase(loginUser.getRole())) {
            throw new BusinessException(ResultCode.FORBIDDEN, "admin permission required");
        }
        return loginUser;
    }
}
