package com.project.diss.dto;

import lombok.Data;

@Data
public class CompanyDocumentSaveDto {
    private Long id;
    private String title;
    private String text;
    private String keywords;
    private Long userId;
    private FileDto file;
}
