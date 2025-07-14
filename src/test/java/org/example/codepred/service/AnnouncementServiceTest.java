package org.example.codepred.service;

import org.example.codepred.dto.AnnouncementResponseDto;
import org.example.codepred.entity.Announcement;
import org.example.codepred.exception.AnnouncementNotFoundException;
import org.example.codepred.repository.AnnouncementRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
@ExtendWith(MockitoExtension.class)
class AnnouncementServiceTest {
    @Mock
    private AnnouncementRepository repository;
    @InjectMocks
    private AnnouncementService service;

    @BeforeEach
    void setUp() {
        service = new AnnouncementService(repository);
    }

    @Test
    void getAnnouncementById_shouldIncrementViews() {
        Announcement existing = new Announcement();
        existing.setId(1L);
        existing.setContent("Old content");
        existing.setViews(5);
        existing.setCreatedAt(LocalDateTime.now());

        when(repository.findById(existing.getId())).thenReturn(Optional.of(existing));
        when(repository.save(any())).thenReturn(existing);

        AnnouncementResponseDto response = service.getAnnouncementById(1L);

        assertEquals(6, response.getViews());
        assertEquals(existing.getContent(), response.getContent());
        verify(repository).save(existing);
    }
    @Test
    void getAnnouncementById_shouldThrowIfNotFound() {
        when(repository.findById(anyLong())).thenReturn(Optional.empty());

        AnnouncementNotFoundException ex = assertThrows(AnnouncementNotFoundException.class,
                () -> service.getAnnouncementById(1L));
        assertTrue(ex.getMessage().contains("not found"));
    }
}