package org.example.codepred.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.codepred.dto.AnnouncementRequestDto;
import org.example.codepred.dto.AnnouncementResponseDto;
import org.example.codepred.entity.Announcement;
import org.example.codepred.repository.AnnouncementRepository;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class AnnouncementControllerTest {

    @Autowired
     MockMvc mockMvc;

    @MockitoSpyBean
    AnnouncementRepository announcementRepository;


    @Autowired
    ObjectMapper objectMapper;



    @BeforeEach
    void setup() {
     announcementRepository.deleteAll();
    }

    @Test
    void createAnnouncement_shouldReturnCreated() throws Exception {
        AnnouncementRequestDto request=new AnnouncementRequestDto("test");
        AnnouncementResponseDto response = new AnnouncementResponseDto();
        response.setContent(request.getContent());


        mockMvc.perform(post("/api/announcements")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(Matchers.any(Number.class)))
                .andExpect(jsonPath("$.content").value(response.getContent()))
                .andExpect(jsonPath("$.views").value(0));
    }

    @Test
    void createAnnouncement_ShouldReturnContentIsBlank() throws Exception {
        AnnouncementRequestDto request = new AnnouncementRequestDto("");

        mockMvc.perform(post("/api/announcements")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.content").value("Must not be blank"));
    }






    @Test
    void getAnnouncementById_shouldReturnAnnouncement() throws Exception {
        Announcement savedAnnouncement = createAnnouncement("test", 10);
        AnnouncementResponseDto response = toResponseDto(savedAnnouncement);


        mockMvc.perform(get("/api/announcements/{id}", savedAnnouncement.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(response.getId()))
                .andExpect(jsonPath("$.content").value(response.getContent()))
                .andExpect(jsonPath("$.views").value(response.getViews()+1));
    }

    @Test
    void getAnnouncementById_shouldReturnNotFound() throws Exception {
        Mockito.when(announcementRepository.findById(anyLong())).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/announcements/{id}", 1L))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Announcement with id 1 not found"));
    }

    @Test
    void deleteAnnouncement_shouldReturnNoContent() throws Exception {
        Announcement savedAnnouncement = createAnnouncement("abc", 0);
        Assertions.assertEquals(1, announcementRepository.findAll().size());

        mockMvc.perform(delete("/api/announcements/{id}", savedAnnouncement.getId()))
                .andExpect(status().isNoContent());
        Assertions.assertEquals(0, announcementRepository.findAll().size());


    }

    @Test
    void updateAnnouncement_shouldReturnUpdated() throws Exception {
        Announcement savedAnnouncement = createAnnouncement("abc", 0);
        Assertions.assertEquals(1, announcementRepository.findAll().size());

        AnnouncementRequestDto request = new AnnouncementRequestDto("test");
        AnnouncementResponseDto response = toResponseDto(savedAnnouncement);
        response.setContent(request.getContent());

        mockMvc.perform(put("/api/announcements/{id}", savedAnnouncement.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(savedAnnouncement.getId()))
                .andExpect(jsonPath("$.content").value(response.getContent()))
                .andExpect(jsonPath("$.views").value(response.getViews()));
    }









    private Announcement createAnnouncement(String content, int views) {
        Announcement a = new Announcement();
        a.setContent(content);
        a.setViews(views);
        a.setCreatedAt(LocalDateTime.now());
        return announcementRepository.save(a);
    }
    private AnnouncementResponseDto toResponseDto(Announcement announcement) {
        AnnouncementResponseDto dto = new AnnouncementResponseDto();
        dto.setId(announcement.getId());
        dto.setContent(announcement.getContent());
        dto.setCreatedAt(announcement.getCreatedAt());
        dto.setViews(announcement.getViews());
        return dto;
    }

}