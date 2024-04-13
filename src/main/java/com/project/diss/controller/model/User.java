package com.project.diss.controller.model;

import lombok.Data;

@Data
public class User {

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
    private String type;
}
