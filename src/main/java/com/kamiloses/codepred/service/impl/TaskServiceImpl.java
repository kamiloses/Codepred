package com.kamiloses.codepred.service.impl;

import com.kamiloses.codepred.dto.TaskRequestDTO;
import com.kamiloses.codepred.dto.TaskResponseDTO;
import com.kamiloses.codepred.entity.Task;
import com.kamiloses.codepred.exception.TaskNotFoundException;
import com.kamiloses.codepred.mapper.TaskMapper;
import com.kamiloses.codepred.repository.TaskRepository;
import com.kamiloses.codepred.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {


    private final TaskRepository taskRepository;

    @Override
    public TaskResponseDTO createTask(TaskRequestDTO taskRequestDTO) {
        Task taskEntity = TaskMapper.toEntity(taskRequestDTO);
        Task savedTask = taskRepository.save(taskEntity);
        return TaskMapper.toDTO(savedTask);
    }

    //Zakładam, że paginacja nie jest  wymagana w tym zadaniu.
    @Override
    public List<TaskResponseDTO> getAllTasks() {
        List<Task> tasks = taskRepository.findAll();
        return tasks.stream()
                .map(TaskMapper::toDTO)
                .toList();
    }

    @Override
    public TaskResponseDTO getTaskById(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException("Task not found with id " + id));
        return TaskMapper.toDTO(task);
    }

    @Override
    public TaskResponseDTO updateTask(Long id, TaskRequestDTO taskRequestDTO) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException("Task not found with id " + id));

        task.setTitle(taskRequestDTO.getTitle());
        task.setDescription(taskRequestDTO.getDescription());
        task.setStatus(taskRequestDTO.getStatus());

        Task updatedTask = taskRepository.save(task);
        return TaskMapper.toDTO(updatedTask);
    }

    @Override
    public void deleteTask(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(("Task not found with id " + id)));
        taskRepository.delete(task);
    }
}
