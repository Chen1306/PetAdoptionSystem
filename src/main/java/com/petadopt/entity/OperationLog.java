package com.petadopt.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class OperationLog {

    private Long id;
    private Long operatorId;
    private String operatorRole;
    private String module;
    private String action;
    private Long targetId;
    private String content;
    private String ip;
    private LocalDateTime createdAt;
}
