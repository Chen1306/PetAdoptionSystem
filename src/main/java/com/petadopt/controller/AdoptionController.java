package com.petadopt.controller;

import com.petadopt.common.ApiResponse;
import com.petadopt.dto.AdoptionCreateRequest;
import com.petadopt.entity.AdoptRecord;
import com.petadopt.service.AdoptionService;
import com.petadopt.util.AuthContext;
import com.petadopt.vo.LoginUser;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/api/adoptions")
public class AdoptionController {

    private final AdoptionService adoptionService;

    public AdoptionController(AdoptionService adoptionService) {
        this.adoptionService = adoptionService;
    }

    @PostMapping
    public ApiResponse<Long> create(@Validated @RequestBody AdoptionCreateRequest request,
                                    HttpServletRequest servletRequest) {
        LoginUser loginUser = AuthContext.getLoginUser(servletRequest);
        return ApiResponse.success("application created", adoptionService.createApplication(loginUser, request));
    }

    @GetMapping("/my")
    public ApiResponse<List<AdoptRecord>> myList(HttpServletRequest servletRequest) {
        LoginUser loginUser = AuthContext.getLoginUser(servletRequest);
        return ApiResponse.success(adoptionService.listMyApplications(loginUser.getUserId()));
    }

    @PutMapping("/{id}/cancel")
    public ApiResponse<Void> cancel(@PathVariable Long id, HttpServletRequest servletRequest) {
        LoginUser loginUser = AuthContext.getLoginUser(servletRequest);
        adoptionService.cancelApplication(loginUser, id);
        return ApiResponse.success("cancel success", null);
    }
}
