package com.petadopt.entity;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Data
public class User {

    private Long id;

    @NotBlank(message = "username can not be blank")
    @Size(min = 3, max = 20, message = "username length must be 3-20")
    private String username;

    @NotBlank(message = "password can not be blank")
    private String password;

    @Pattern(regexp = "^$|^\\d{11}$", message = "phone must be 11 digits")
    private String phone;

    private String role;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
