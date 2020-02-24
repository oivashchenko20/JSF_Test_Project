package com.example.dto;

import lombok.Data;

@Data
public class PasswordDto {
    private String oldPassword;
    private String confirmPassword;
    private String newPassword;
}
