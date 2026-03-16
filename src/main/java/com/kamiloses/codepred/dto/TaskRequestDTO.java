package com.kamiloses.codepred.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Setter
@NoArgsConstructor
@Getter//todo zostawic czy nie oraz dodać walidacje
public class TaskRequestDTO {

    private String title;

    private String description;

    private String status;



}
