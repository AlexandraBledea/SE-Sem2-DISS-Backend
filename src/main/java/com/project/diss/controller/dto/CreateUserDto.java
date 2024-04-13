package com.project.diss.controller.dto;

import com.project.diss.persistance.entity.enums.UserType;
import lombok.Data;

@Data
public class CreateUserDto {
    private String email;
    private String password;
    private String firstname;
    private String lastname;
    private String phoneNumber;
    private String role;
    private String department;
    private String location;
    private Integer level;
    private Integer points;
    private UserType type;
}
