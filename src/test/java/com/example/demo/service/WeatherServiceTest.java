package com.example.demo.service;

import com.example.demo.domain.Diary;
import com.example.demo.dto.DiaryRequestDTO;
import com.example.demo.dto.DiaryResponseDTO;
import com.example.demo.dto.WeatherDTO;
import com.example.demo.repository.DateWeatherRepository;
import com.example.demo.repository.DiaryRepository;
import com.example.demo.util.WeatherApiClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class WeatherServiceTest {

    private WeatherService weatherService;
    private DiaryService diaryService;

    @Mock
    private DiaryRepository diaryRepository;

    @Mock
    private DateWeatherRepository dateWeatherRepository;

    @Mock
    private WeatherApiClient weatherApiClient;

    @BeforeEach
    void setUp() {
        weatherService = new WeatherService(dateWeatherRepository, weatherApiClient);
        diaryService = new DiaryService(diaryRepository, dateWeatherRepository, weatherService);
    }

    @Test
    void createDiaryTest() {
        // Given
        LocalDate date = LocalDate.now();
        String text = "배가 고파서 배달시켜 먹었다";
        DiaryRequestDTO requestDTO = new DiaryRequestDTO(date, text);

        // Mock WeatherDTO
        WeatherDTO weatherDTO = new WeatherDTO();
        weatherDTO.setWeather("Clear");
        weatherDTO.setIcon("01d");
        weatherDTO.setTemperature(20.0);

// 중복 선언된 변수 제거
        double latitude = 37.5172;
        double longitude = 127.0473;

// 이미 선언된 weatherDTO와 date를 재사용
// Mock 설정
        given(weatherApiClient.getWeatherData(latitude, longitude)).willReturn(weatherDTO);
        given(dateWeatherRepository.findById(date)).willReturn(Optional.empty());

        Diary diary = Diary.builder()
            .date(date)
            .text(text)
            .weather("Clear")
            .icon("01d")
            .temperature(20.0)
            .build();

        given(diaryRepository.save(any(Diary.class))).willReturn(diary);

        // When
        DiaryResponseDTO response = diaryService.createDiary(date, requestDTO);

        // Then
        assertNotNull(response);
        assertEquals(text, response.getText());
        assertEquals(date, response.getDate());
        assertEquals("Clear", response.getWeather());
        assertEquals("01d", response.getIcon());
        assertEquals(20.0, response.getTemperature());
    }
}