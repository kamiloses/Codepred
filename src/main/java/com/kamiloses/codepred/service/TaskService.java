package com.kamiloses.codepred.service;

import com.kamiloses.codepred.dto.TaskRequestDTO;
import com.kamiloses.codepred.dto.TaskResponseDTO;

import java.util.List;

public interface TaskService {

    TaskResponseDTO createTask(TaskRequestDTO task);

    List<TaskResponseDTO> getAllTasks();

    TaskResponseDTO getTaskById(Long id);

    TaskResponseDTO updateTask(Long id, TaskRequestDTO task);

    void deleteTask(Long id);


}
