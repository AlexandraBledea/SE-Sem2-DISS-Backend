package com.project.diss.dto;

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