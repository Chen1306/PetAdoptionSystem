package com.petadopt.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class AdoptionCreateRequest {

    @NotNull(message = "petId can not be null")
    private Long petId;

    @NotBlank(message = "applyDescription can not be blank")
    @Size(max = 500, message = "applyDescription too long")
    private String applyDescription;
}
