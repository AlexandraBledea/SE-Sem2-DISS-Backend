package com.project.diss.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class FileDto {
    private Long id;
    private String name;
    private String type;
    private byte[] buffer;
}
