package com.saro.library.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class LoginUser {
    @NotNull(message = "Email is required")
    @Email
    private String email;
    @NotNull(message = "Password is Required")
    private String password;
}
