package com.kamiloses.codepred.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "DTO representing a task response")
public class TaskResponseDTO {

    @Schema(description = "Unique identifier of the task", example = "1")
    private Long id;

    @Schema(description = "Title of the task", example = "Implement login feature")
    private String title;

    @Schema(description = "Description of the task", example = "Implement login using Spring Security")
    private String description;

    @Schema(description = "Status of the task", example = "NEW")
    private String status;

    @Schema(description = "Date and time when the task was created", example = "2026-03-16T12:34:56")
    private LocalDateTime createdAt;
}