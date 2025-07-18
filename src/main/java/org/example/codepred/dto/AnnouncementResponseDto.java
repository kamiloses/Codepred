package org.example.codepred.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AnnouncementResponseDto {
    private Long id;
    private String content;
    private LocalDateTime createdAt;
    private int views;

}