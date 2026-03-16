package com.kamiloses.codepred.dto;

import com.kamiloses.codepred.enums.Status;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "DTO for creating a task")
public class TaskRequestDTO {

    @NotBlank(message = "Title cannot be empty")
    @Size(max = 100, message = "Title cannot be longer than 100 characters")
    @Schema(description = "Task title", example = "Implement login feature")
    private String title;

    @Size(max = 1000, message = "Description cannot be longer than 1000 characters")
    @Schema(description = "Task description", example = "Implement login using Spring Security")
    private String description;

    @Schema(description = "Task status", example = "NEW")
    private Status status;
}