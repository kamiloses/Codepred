package org.example.codepred.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AnnouncementRequestDto {

    @NotBlank(message = "Must not be blank")
    private String content;
}
