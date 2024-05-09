package com.project.diss.dto;

import lombok.Data;

@Data
public class CommentSaveDto {
    private Long documentId;
    private String text;
    private Long userId;
}