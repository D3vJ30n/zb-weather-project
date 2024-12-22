package com.example.demo.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DiaryRequestDTO {
    @NotNull(message = "날짜는 필수입니다.")
    private LocalDate date;

    @NotNull(message = "일기 내용은 필수입니다.")
    @Size(min = 1, max = 1000, message = "일기 내용은 1자 이상, 1000자 이하이어야 합니다.")
    private String text;
}
