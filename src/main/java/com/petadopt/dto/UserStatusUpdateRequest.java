package com.petadopt.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class UserStatusUpdateRequest {

    @NotBlank(message = "status can not be blank")
    private String status;
}
