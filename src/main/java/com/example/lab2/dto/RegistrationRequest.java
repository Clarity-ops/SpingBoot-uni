package com.example.lab2.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.Data;

import java.time.LocalDate;

@Data // Lombok анотація для генерації геттерів, сеттерів, toString, etc.
public class RegistrationRequest {

    @NotBlank(message = "Username is mandatory") // Поле не може бути пустим
    private String username;

    @NotBlank(message = "Password is mandatory")
    private String password;

    @NotBlank(message = "Email is mandatory")
    @Email(message = "Email should be valid") // Перевірка формату email
    private String email;

    @NotNull(message = "Birthday is mandatory")
    @Past(message = "Birthday must be in the past") // Дата має бути в минулому
    private LocalDate birthday;

    private String phoneNumber; // Необов'язкове поле, без валідації
}