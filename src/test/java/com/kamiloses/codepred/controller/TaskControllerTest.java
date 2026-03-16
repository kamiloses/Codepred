package com.kamiloses.codepred.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.kamiloses.codepred.dto.TaskRequestDTO;
import com.kamiloses.codepred.dto.TaskResponseDTO;
import com.kamiloses.codepred.enums.Status;
import com.kamiloses.codepred.service.TaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private TaskService taskService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private TaskRequestDTO taskRequest;
    private TaskResponseDTO taskResponse;

    @BeforeEach
    void setUp() {
        taskRequest = new TaskRequestDTO();
        taskRequest.setTitle("Test Task");
        taskRequest.setDescription("Description");
        taskRequest.setStatus(Status.NEW);

        taskResponse = new TaskResponseDTO();
        taskResponse.setId(1L);
        taskResponse.setTitle("Test Task");
        taskResponse.setDescription("Description");
        taskResponse.setStatus("NEW");
    }

    // --------------- CREATE ---------------
    @Test
    void createTask_ShouldReturnCreatedTask() throws Exception {
        Mockito.when(taskService.createTask(any(TaskRequestDTO.class))).thenReturn(taskResponse);

        mockMvc.perform(post("/api/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(taskRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(taskResponse.getId()))
                .andExpect(jsonPath("$.title").value(taskResponse.getTitle()));
    }

    // --------------- GET ALL ---------------
    @Test
    void getAllTasks_ShouldReturnListOfTasks() throws Exception {
        List<TaskResponseDTO> tasks = List.of(taskResponse);
        Mockito.when(taskService.getAllTasks()).thenReturn(tasks);

        mockMvc.perform(get("/api/tasks"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(taskResponse.getId()))
                .andExpect(jsonPath("$[0].title").value(taskResponse.getTitle()));
    }

    // --------------- GET BY ID ---------------
    @Test
    void getTaskById_ShouldReturnTask() throws Exception {
        Mockito.when(taskService.getTaskById(1L)).thenReturn(taskResponse);

        mockMvc.perform(get("/api/tasks/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(taskResponse.getId()))
                .andExpect(jsonPath("$.title").value(taskResponse.getTitle()));
    }

    // --------------- UPDATE ---------------
    @Test
    void updateTask_ShouldReturnUpdatedTask() throws Exception {
        Mockito.when(taskService.updateTask(eq(1L), any(TaskRequestDTO.class))).thenReturn(taskResponse);

        mockMvc.perform(put("/api/tasks/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(taskRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(taskResponse.getId()))
                .andExpect(jsonPath("$.title").value(taskResponse.getTitle()));
    }

    // --------------- DELETE ---------------
    @Test
    void deleteTask_ShouldReturnNoContent() throws Exception {
        Mockito.doNothing().when(taskService).deleteTask(1L);

        mockMvc.perform(delete("/api/tasks/{id}", 1L))
                .andExpect(status().isNoContent());
    }
}