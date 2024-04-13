package com.project.diss.controller.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ErrorResponse {

    private String errorCode;
    private String errorName;
    private String errorMessage;
}