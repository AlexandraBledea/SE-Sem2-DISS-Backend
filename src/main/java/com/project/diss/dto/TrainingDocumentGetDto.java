package com.project.diss.dto;

import lombok.Data;

@Data
public class TrainingDocumentGetDto {
    private Long id;
    private String title;
    private Integer requiredLevel;
    private Integer reward;
    private String text;
    private UserDocumentDto user;
}
