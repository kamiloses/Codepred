package com.kamiloses.codepred.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TaskResponseDTO {

    private Long id;
    private String title;
    private String description;
    private String status;
    private LocalDateTime createdAt;
}