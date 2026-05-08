package com.petadopt.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class PetReviewRequest {

    @NotBlank(message = "reviewStatus can not be blank")
    private String reviewStatus;

    @Size(max = 255, message = "reviewRemark too long")
    private String reviewRemark;
}
