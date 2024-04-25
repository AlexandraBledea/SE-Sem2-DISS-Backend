package com.project.diss.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SaveTrainingDocumentDto {
    private Long id;
    private String title;
    private String text;
    private String keywords;
    private Integer pointsRequired;
    private Integer reward;
    private Integer totalPages;
    private Long userId;
    private FileDto file;
}
