package com.project.diss.dto;

import lombok.Data;

@Data
public class TrainingDocumentSaveDto {
    private Long id;
    private String title;
    private String text;
    private String keywords;
    private Integer requiredLevel;
    private Integer reward;
    private Long userId;
    private FileDto file;
}
