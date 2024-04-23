package com.project.diss.controller.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class EmployeeDocumentDto {
    private Long id;
    private String title;
    private String text;
    private byte[] document;
    private String keywords;
    private LocalDateTime lastModified;
    private boolean visibility;
    private Long userId;
    private String userFirstname;
    private String userLastname;
}
