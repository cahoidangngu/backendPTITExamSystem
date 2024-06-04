package com.quanchun.backendexamsystem.models;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTO {
    @NotBlank
    private String username;
    @NotBlank
    private String password;
    @NotBlank
    private String fullName;
    private Date dob;
    private String studyClass;
    private String phone;
    private Boolean gender;
    private String address;
    private String imagePath;
    @NotNull
    private Long roleId;
}
