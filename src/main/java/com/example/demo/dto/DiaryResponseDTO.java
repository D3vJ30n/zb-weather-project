package com.example.demo.dto;

import com.example.demo.domain.Diary;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DiaryResponseDTO {
    private LocalDate date;
    private String weather;
    private String icon;
    private double temperature;
    private String text;

    public static DiaryResponseDTO from(Diary diary) {
        return DiaryResponseDTO.builder()
                .date(diary.getDate())
                .weather(diary.getWeather())
                .icon(diary.getIcon())
                .temperature(diary.getTemperature())
                .text(diary.getText())
                .build();
    }
}