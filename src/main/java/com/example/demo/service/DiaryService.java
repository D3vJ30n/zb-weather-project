package com.example.demo.service;

import com.example.demo.domain.DateWeather;
import com.example.demo.domain.Diary;
import com.example.demo.dto.DiaryRequestDTO;
import com.example.demo.dto.DiaryResponseDTO;
import com.example.demo.repository.DateWeatherRepository;
import com.example.demo.repository.DiaryRepository;
import com.example.demo.util.DateUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DiaryService {
    private final DiaryRepository diaryRepository;
    private final DateWeatherRepository dateWeatherRepository;
    private final WeatherService weatherService;

    @Transactional
    public DiaryResponseDTO createDiary(LocalDate date, DiaryRequestDTO requestDTO) {
        String text = requestDTO.getText();

        // WeatherService를 통해 날씨 정보 조회
        DateWeather dateWeather = weatherService.getDateWeather(date);

        Diary diary = Diary.builder()
            .date(date)
            .text(text)
            .weather(dateWeather.getWeather())
            .icon(dateWeather.getIcon())
            .temperature(dateWeather.getTemperature())
            .build();

        diaryRepository.save(diary);
        return DiaryResponseDTO.from(diary);
    }

    public List<DiaryResponseDTO> readDiary(LocalDate date) {
        DateUtil.validateDate(date);
        return diaryRepository.findAllByDate(date).stream()
            .map(DiaryResponseDTO::from)
            .collect(Collectors.toList());
    }

    public List<DiaryResponseDTO> readDiaries(LocalDate startDate, LocalDate endDate) {
        DateUtil.validateDateRange(startDate, endDate);
        return diaryRepository.findAllByDateBetween(startDate, endDate).stream()
            .map(DiaryResponseDTO::from)
            .collect(Collectors.toList());
    }

    @Transactional
    public void updateDiary(LocalDate date, DiaryRequestDTO requestDTO) {
        DateUtil.validateDate(date);
        Diary diary = diaryRepository.findFirstByDate(date)
            .orElseThrow(() -> new IllegalArgumentException("해당 날짜의 일기가 존재하지 않습니다."));
        diary.setText(requestDTO.getText());
    }

    @Transactional
    public void deleteDiary(LocalDate date) {
        DateUtil.validateDate(date);
        diaryRepository.deleteAllByDate(date);
    }
}