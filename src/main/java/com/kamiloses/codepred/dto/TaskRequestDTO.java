package com.kamiloses.codepred.dto;

import com.kamiloses.codepred.enums.Status;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TaskRequestDTO {
    @NotBlank(message = "Title cannot be empty")
    @Size(max = 100, message = "Title cannot be longer than 100 characters")
    private String title;

    @Size(max = 1000, message = "Description cannot be longer than 1000 characters")
    private String description;

    private Status status;


}
