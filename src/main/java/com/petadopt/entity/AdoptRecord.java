package com.petadopt.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AdoptRecord {

    private Long id;
    private Long userId;
    private Long petId;
    private String applyDescription;
    private String status;
    private String auditRemark;
    private Long auditedBy;
    private LocalDateTime auditedAt;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
