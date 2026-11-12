package com.petadopt.config;

import com.petadopt.common.BusinessException;
import com.petadopt.common.ResultCode;
import com.petadopt.service.TokenService;
import com.petadopt.vo.LoginUser;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
/*
认证拦截器：校验token,把用户放到request里面
*/
@Component
public class AuthInterceptor implements HandlerInterceptor {

    private final TokenService tokenService;
    private final AntPathMatcher pathMatcher = new AntPathMatcher();

    private static final List<String> EXCLUDE_PATTERNS = List.of(
            "/api/auth/register",
            "/api/auth/login",
            "/api/pets",
            "/api/pets/*"
    );

    public AuthInterceptor(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        // OPTIONS preflight has no auth header, let CORS handler process it
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            return true;
        }
        String uri = request.getRequestURI();
        if (isExcluded(request.getMethod(), uri)) {
            return true;
        }
        String authorization = request.getHeader("Authorization");
        if (!StringUtils.hasText(authorization) || !authorization.startsWith("Bearer ")) {
            throw new BusinessException(ResultCode.UNAUTHORIZED, "Please login first");
        }
        LoginUser loginUser = tokenService.parseToken(authorization.substring(7));
        request.setAttribute("loginUser", loginUser);
        return true;
    }

    private boolean isExcluded(String method, String uri) {
        if (!"GET".equalsIgnoreCase(method) && uri.startsWith("/api/pets")) {
            return false;
        }
        return EXCLUDE_PATTERNS.stream().anyMatch(pattern -> pathMatcher.match(pattern, uri));
    }
}
