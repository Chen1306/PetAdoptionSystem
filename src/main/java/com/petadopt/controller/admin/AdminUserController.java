package com.petadopt.controller.admin;

import com.petadopt.common.ApiResponse;
import com.petadopt.dto.UserStatusUpdateRequest;
import com.petadopt.entity.User;
import com.petadopt.service.UserService;
import com.petadopt.util.AuthContext;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/api/admin/users")
public class AdminUserController {

    private final UserService userService;

    public AdminUserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ApiResponse<List<User>> list(HttpServletRequest request) {
        AuthContext.getAdmin(request);
        return ApiResponse.success(userService.listAllUsers());
    }

    @PutMapping("/{id}/status")
    public ApiResponse<Void> updateStatus(@PathVariable Long id,
                                          @Validated @RequestBody UserStatusUpdateRequest request,
                                          HttpServletRequest servletRequest) {
        AuthContext.getAdmin(servletRequest);
        userService.updateStatus(id, request.getStatus());
        return ApiResponse.success("status updated", null);
    }
}
