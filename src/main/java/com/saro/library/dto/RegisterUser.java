package com.saro.library.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RegisterUser {

    @NotNull(message = "Email is Required")
    @Email
    private String email;

    @NotNull(message = "Password is required")
    private String password;
}
