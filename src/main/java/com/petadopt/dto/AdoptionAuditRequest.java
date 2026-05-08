package com.petadopt.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class AdoptionAuditRequest {

    @NotBlank(message = "status can not be blank")
    private String status;

    @Size(max = 255, message = "auditRemark too long")
    private String auditRemark;
}
