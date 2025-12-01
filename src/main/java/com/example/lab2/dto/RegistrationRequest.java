package com.example.lab2.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import java.time.LocalDate;

@Data
public class RegistrationRequest {
    @NotBlank(message = "Username is mandatory")
    private String username;

    @NotBlank(message = "Password is mandatory")
    private String password;

    @NotBlank(message = "Email is mandatory")
    @Email(message = "Email should be valid")
    private String email;

    @NotNull(message = "Birthday is mandatory")
    @Past(message = "Birthday must be in the past")
    private LocalDate birthday;

    @Pattern(regexp = "^\\+380\\d{9}$", message = "Phone number must start with +380 and contain 12 digits total")
    private String phoneNumber;
}