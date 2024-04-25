package com.project.diss.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class EmployeeDocumentDto {
    private Long id;
    private String title;
    private String text;
    private String keywords;
    private LocalDateTime lastModified;
    private boolean visibility;
    private UserDto user; //TODO - create another DTO for user only with necessary fields and use it here
    private FileDto file;
}
