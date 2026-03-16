package com.kamiloses.codepred.dto;

import lombok.Builder;

import java.time.LocalDateTime;
@Builder
public class TaskResponseDTO {


    private Long id;
    private String title;
    private String description;
    private String status;//todo string czy enum
    private LocalDateTime createdAt;

}
