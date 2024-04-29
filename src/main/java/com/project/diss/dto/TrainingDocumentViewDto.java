package com.project.diss.dto;

import lombok.Data;


@Data
public class TrainingDocumentViewDto {
    private Long id;
    private String title;
    private Integer reward;
    private UserDocumentDto user;
    private FileDto file;
    private BadgeDto badge;
}
