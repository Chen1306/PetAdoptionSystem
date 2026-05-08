package com.petadopt.entity;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Data
public class Pet {

    private Long id;

    @NotBlank(message = "petName can not be blank")
    @Size(max = 50, message = "petName too long")
    private String petName;

    @NotBlank(message = "type can not be blank")
    private String type;

    @Min(value = 0, message = "age must be >= 0")
    private Integer age;

    private String gender;
    private String imageUrl;

    @Size(max = 500, message = "description too long")
    private String description;

    private String reviewStatus;
    private String adoptStatus;
    private Long reviewerId;
    private String reviewRemark;
    private LocalDateTime reviewedAt;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
