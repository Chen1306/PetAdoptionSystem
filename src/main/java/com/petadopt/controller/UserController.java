package com.petadopt.controller;

import com.petadopt.common.ApiResponse;
import com.petadopt.dto.ProfileUpdateRequest;
import com.petadopt.entity.User;
import com.petadopt.service.UserService;
import com.petadopt.util.AuthContext;
import com.petadopt.vo.LoginUser;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/profile")
    public ApiResponse<User> profile(HttpServletRequest request) {
        LoginUser loginUser = AuthContext.getLoginUser(request);
        return ApiResponse.success(userService.getById(loginUser.getUserId()));
    }

    @PutMapping("/profile")
    public ApiResponse<Void> updateProfile(@Validated @RequestBody ProfileUpdateRequest updateRequest,
                                           HttpServletRequest request) {
        LoginUser loginUser = AuthContext.getLoginUser(request);
        userService.updateProfile(loginUser.getUserId(), updateRequest);
        return ApiResponse.success("update success", null);
    }
}
