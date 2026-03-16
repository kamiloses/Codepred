package com.kamiloses.codepred.mapper;

import com.kamiloses.codepred.dto.TaskRequestDTO;
import com.kamiloses.codepred.dto.TaskResponseDTO;
import com.kamiloses.codepred.entity.Task;
import com.kamiloses.codepred.enums.Status;

public class TaskMapper {

    public static TaskResponseDTO toDTO(Task task) {
        if (task == null) return null;

        return TaskResponseDTO.builder()
                .id(task.getId())
                .title(task.getTitle())
                .description(task.getDescription())
                .status(task.getStatus().name())
                .createdAt(task.getCreatedAt())
                .build();
    }

    public static Task toEntity(TaskRequestDTO dto) {
        if (dto == null) return null;

        Task task = new Task();
        task.setTitle(dto.getTitle());
        task.setDescription(dto.getDescription());
        task.setStatus(dto.getStatus() != null && !dto.getStatus().isEmpty()
                ? Status.valueOf(dto.getStatus())
                : Status.NEW); //todo popraw
        return task;
    }
}