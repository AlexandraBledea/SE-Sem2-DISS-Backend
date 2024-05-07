package com.project.diss.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CompanyDocumentDto {
    private Long id;
    private String title;
    private String text;
    private String keywords;
    private LocalDateTime lastModified;
    private UserDocumentDto user;
    private FileDto file;
}
