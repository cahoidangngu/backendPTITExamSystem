package com.quanchun.backendexamsystem.models;

import com.quanchun.backendexamsystem.entities.Role;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.awt.image.RasterOp;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTO {
    private Long userId;
    @NotBlank
    private String username;
    @NotBlank
    private String password;
    @NotBlank
    private String fullName;
    private Date dob;
    private RoleDTO role;
    private String studyClass;
    private String phone;
    private Boolean gender;
    private String address;
    private String imagePath;
}
