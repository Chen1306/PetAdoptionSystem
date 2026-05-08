package com.petadopt.controller.admin;

import com.petadopt.common.ApiResponse;
import com.petadopt.dto.AdoptionAuditRequest;
import com.petadopt.entity.AdoptRecord;
import com.petadopt.service.AdoptionService;
import com.petadopt.util.AuthContext;
import com.petadopt.vo.LoginUser;
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
@RequestMapping("/api/admin/adoptions")
public class AdminAdoptionController {

    private final AdoptionService adoptionService;

    public AdminAdoptionController(AdoptionService adoptionService) {
        this.adoptionService = adoptionService;
    }

    @GetMapping
    public ApiResponse<List<AdoptRecord>> list(HttpServletRequest request) {
        AuthContext.getAdmin(request);
        return ApiResponse.success(adoptionService.listAllApplications());
    }

    @PutMapping("/{id}/audit")
    public ApiResponse<Void> audit(@PathVariable Long id,
                                   @Validated @RequestBody AdoptionAuditRequest request,
                                   HttpServletRequest servletRequest) {
        LoginUser loginUser = AuthContext.getAdmin(servletRequest);
        adoptionService.auditApplication(loginUser, id, request);
        return ApiResponse.success("audit success", null);
    }
}
