package com.kamiloses.codepred.service.impl;

import com.kamiloses.codepred.dto.TaskRequestDTO;
import com.kamiloses.codepred.dto.TaskResponseDTO;
import com.kamiloses.codepred.entity.Task;
import com.kamiloses.codepred.enums.Status;
import com.kamiloses.codepred.exception.TaskNotFoundException;
import com.kamiloses.codepred.repository.TaskRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
class TaskServiceImplTest {

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskServiceImpl taskServiceImpl;



    // --------------- CREATE ---------------
    @Test
    void createTask_shouldSaveTask() {
        Task savedTask = new Task();
        savedTask.setTitle("Login feature");
        savedTask.setDescription("Implement login with Spring Security");
        savedTask.setStatus(Status.NEW);

        when(taskRepository.save(any(Task.class))).thenReturn(savedTask);

        TaskRequestDTO requestDTO = new TaskRequestDTO("Login feature",
                "Implement login with Spring Security", Status.NEW);

        TaskResponseDTO result = taskServiceImpl.createTask(requestDTO);

        assertEquals("Login feature", result.getTitle());
        assertEquals("Implement login with Spring Security", result.getDescription());
        assertEquals("NEW", result.getStatus());

        verify(taskRepository, times(1)).save(any(Task.class));
    }

    // --------------- GET ALL ---------------
    @Test
    void getAllTasks_shouldReturnListOfTasks() {
        Task task1 = new Task();
        task1.setTitle("Task 1");
        task1.setStatus(Status.NEW);

        Task task2 = new Task();
        task2.setTitle("Task 2");
        task2.setStatus(Status.IN_PROGRESS);

        when(taskRepository.findAll()).thenReturn(List.of(task1, task2));

        List<TaskResponseDTO> result = taskServiceImpl.getAllTasks();

        assertEquals(2, result.size());
        assertEquals("Task 1", result.get(0).getTitle());
        assertEquals("Task 2", result.get(1).getTitle());

        verify(taskRepository, times(1)).findAll();
    }

    // --------------- GET BY ID ---------------
    @Test
    void getTaskById_shouldReturnTask_whenExists() {
        Task task = new Task();
        task.setTitle("Task 1");
        task.setStatus(Status.NEW);

        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));

        TaskResponseDTO result = taskServiceImpl.getTaskById(1L);

        assertEquals("Task 1", result.getTitle());
        assertEquals("NEW", result.getStatus());
        verify(taskRepository, times(1)).findById(1L);
    }

    @Test
    void getTaskById_shouldThrowException_whenNotFound() {
        when(taskRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(TaskNotFoundException.class, () -> taskServiceImpl.getTaskById(1L));
        verify(taskRepository, times(1)).findById(1L);
    }

    // --------------- UPDATE ---------------
    @Test
    void updateTask_shouldUpdateAndReturnTask_whenExists() {
        Task existingTask = new Task();
        existingTask.setTitle("Old Title");
        existingTask.setStatus(Status.NEW);

        when(taskRepository.findById(1L)).thenReturn(Optional.of(existingTask));
        when(taskRepository.save(any(Task.class))).thenAnswer(i -> i.getArgument(0));

        TaskRequestDTO updateDTO = new TaskRequestDTO("New Title", "Updated description", Status.DONE);

        TaskResponseDTO result = taskServiceImpl.updateTask(1L, updateDTO);

        assertEquals("New Title", result.getTitle());
        assertEquals("Updated description", result.getDescription());
        assertEquals("DONE", result.getStatus());

        verify(taskRepository, times(1)).findById(1L);
        verify(taskRepository, times(1)).save(existingTask);
    }

    @Test
    void updateTask_shouldThrowException_whenNotFound() {
        when(taskRepository.findById(1L)).thenReturn(Optional.empty());

        TaskRequestDTO updateDTO = new TaskRequestDTO("New Title", "Updated description", Status.DONE);

        assertThrows(TaskNotFoundException.class, () -> taskServiceImpl.updateTask(1L, updateDTO));
        verify(taskRepository, times(1)).findById(1L);
        verify(taskRepository, never()).save(any(Task.class));
    }

    // --------------- DELETE ---------------
    @Test
    void deleteTask_shouldDeleteTask_whenExists() {
        Task existingTask = new Task();
        existingTask.setTitle("Task to delete");

        when(taskRepository.findById(1L)).thenReturn(Optional.of(existingTask));

        taskServiceImpl.deleteTask(1L);

        verify(taskRepository, times(1)).findById(1L);
        verify(taskRepository, times(1)).delete(existingTask);
    }

    @Test
    void deleteTask_shouldThrowException_whenNotFound() {
        when(taskRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(TaskNotFoundException.class, () -> taskServiceImpl.deleteTask(1L));

        verify(taskRepository, times(1)).findById(1L);
        verify(taskRepository, never()).delete(any(Task.class));
    }
}