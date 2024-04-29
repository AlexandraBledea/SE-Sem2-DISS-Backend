package com.project.diss.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
//Used when we display the documents in a list
public class EmployeeDocumentGetDto {
    private Long id;
    private String title;
    private LocalDateTime lastModified;
    private UserDocumentDto user;
}
