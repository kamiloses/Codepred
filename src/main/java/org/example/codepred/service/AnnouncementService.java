package org.example.codepred.service;

import lombok.RequiredArgsConstructor;
import org.example.codepred.dto.AnnouncementRequestDto;
import org.example.codepred.dto.AnnouncementResponseDto;
import org.example.codepred.entity.Announcement;
import org.example.codepred.exception.AnnouncementNotFoundException;
import org.example.codepred.repository.AnnouncementRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AnnouncementService {

    private final AnnouncementRepository repository;


    public AnnouncementResponseDto saveAnnouncement(AnnouncementRequestDto announcementDto) {
        Announcement announcement = new Announcement();
        announcement.setContent(announcementDto.getContent());
        announcement.setCreatedAt(LocalDateTime.now());
        return toResponseDto(repository.save(announcement));
    }

    public AnnouncementResponseDto getAnnouncementById(Long id) {
        Announcement announcement = repository.findById(id)
                .orElseThrow(() -> new AnnouncementNotFoundException("Announcement with id " + id + " not found"));
        announcement.setViews(announcement.getViews() + 1);
        return toResponseDto(repository.save(announcement));
    }
    public AnnouncementResponseDto updateAnnouncement(Long id,AnnouncementRequestDto announcementDto) {
        Announcement announcement = repository.findById(id)
                .orElseThrow(() -> new AnnouncementNotFoundException("Announcement with id " + id + " not found"));
        announcement.setContent(announcementDto.getContent());
        return toResponseDto(repository.save(announcement));
    }
    public void deleteAnnouncementById(Long id) {
        if (!repository.existsById(id)) {
            throw new AnnouncementNotFoundException("Announcement with id " + id + " not found");
        }
       repository.deleteById(id);
    }



    public static AnnouncementResponseDto toResponseDto(Announcement announcement) {
        return new AnnouncementResponseDto(
                announcement.getId(),
                announcement.getContent(),
                announcement.getCreatedAt(),
                announcement.getViews());
    }
}