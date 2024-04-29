package com.project.diss.dto;

import lombok.Data;

@Data
public class BadgeDto {
    Long trainingId;
    Long userId;
    String progressStatus;
    Integer currentPage;
    String name;
}
