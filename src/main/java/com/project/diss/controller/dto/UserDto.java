package com.project.diss.controller.dto;

import com.project.diss.persistance.entity.enums.UserType;
import lombok.Data;

@Data
public class UserDto {

    private Long id;
    private String email;
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
