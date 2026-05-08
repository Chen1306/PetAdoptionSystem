package com.petadopt.dto;

import lombok.Data;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
public class ProfileUpdateRequest {

    @Size(min = 3, max = 20, message = "username length must be 3-20")
    private String username;

    @Pattern(regexp = "^$|^\\d{11}$", message = "phone must be 11 digits")
    private String phone;

    @Size(min = 6, max = 20, message = "password length must be 6-20")
    private String password;
}
