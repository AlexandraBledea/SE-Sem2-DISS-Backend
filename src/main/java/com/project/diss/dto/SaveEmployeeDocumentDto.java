package com.project.diss.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SaveEmployeeDocumentDto {
    private Long id;
    private String title;
    private String text;
    private String keywords;
    private boolean visibility;
    private Long userId;
    private FileDto file;
}
