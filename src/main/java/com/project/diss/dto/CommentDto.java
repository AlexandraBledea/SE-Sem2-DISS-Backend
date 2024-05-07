package com.project.diss.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CommentDto {
    private LocalDateTime created;
    private Long documentId;
    private Long id;
    private String text;
    private UserDocumentDto user;
}