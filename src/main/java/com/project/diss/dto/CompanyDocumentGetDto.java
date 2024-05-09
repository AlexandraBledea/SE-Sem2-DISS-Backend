package com.project.diss.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CompanyDocumentGetDto {
    private Long id;
    private String title;
    private LocalDateTime lastModified;
    private UserDocumentDto user;
}
