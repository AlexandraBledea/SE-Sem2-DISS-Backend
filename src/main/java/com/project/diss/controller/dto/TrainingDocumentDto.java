package com.project.diss.controller.dto;

import jakarta.persistence.Column;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TrainingDocumentDto {
    private Long id;
    private String title;
    private String text;
    private byte[] document;
    private String keywords;
    private LocalDateTime created;
    private LocalDateTime lastModified;
    private Integer pointsRequired;
    private Integer reward;
    private Integer totalPages;
    private Long userId;
}
