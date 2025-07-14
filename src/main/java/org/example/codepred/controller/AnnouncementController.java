package org.example.codepred.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.codepred.dto.AnnouncementRequestDto;
import org.example.codepred.dto.AnnouncementResponseDto;
import org.example.codepred.exception.AnnouncementNotFoundException;
import org.example.codepred.service.AnnouncementService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/api/announcements")
@RequiredArgsConstructor
@Validated
public class AnnouncementController {

    private final AnnouncementService announcementService;

    @PostMapping
    public ResponseEntity<AnnouncementResponseDto> createAnnouncement(@Valid @RequestBody AnnouncementRequestDto announcementRequestDto) {
        AnnouncementResponseDto savedAnnouncementDto = announcementService.saveAnnouncement(announcementRequestDto);
        return new ResponseEntity<>(savedAnnouncementDto, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AnnouncementResponseDto> getAnnouncementById(@PathVariable Long id) {
        AnnouncementResponseDto announcementDto = announcementService.getAnnouncementById(id);
        return new ResponseEntity<>(announcementDto, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AnnouncementResponseDto> updateAnnouncement(
            @PathVariable Long id, @Valid @RequestBody AnnouncementRequestDto announcementRequestDto) {
        AnnouncementResponseDto updatedAnnouncement = announcementService.updateAnnouncement(id, announcementRequestDto);
        return new ResponseEntity<>(updatedAnnouncement, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAnnouncement(@PathVariable Long id) {
        announcementService.deleteAnnouncementById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @ExceptionHandler(AnnouncementNotFoundException.class)
    public ResponseEntity<String> handleNotFoundException(AnnouncementNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage())
        );

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }
}
