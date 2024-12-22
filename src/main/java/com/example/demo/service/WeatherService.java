package com.example.demo.service;

import com.example.demo.domain.DateWeather;
import com.example.demo.dto.WeatherDTO;
import com.example.demo.repository.DateWeatherRepository;
import com.example.demo.util.WeatherApiClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class WeatherService {
    private final DateWeatherRepository dateWeatherRepository;
    private final WeatherApiClient weatherApiClient;

    // 강남 지역의 위도와 경도를 상수로 정의
    private static final double GANGNAM_LATITUDE = 37.5172;
    private static final double GANGNAM_LONGITUDE = 127.0473;

    /**
     * 매일 새벽 1시에 강남 지역의 날씨 데이터를 저장합니다.
     */
    @Transactional
    @Scheduled(cron = "0 0 1 * * *") // 매일 새벽 1시에 실행
    public void saveWeatherData() {
        log.info("날씨 데이터 저장 작업을 시작합니다.");
        try {
            // 강남 지역의 날씨 데이터를 API에서 가져오기
            WeatherDTO weatherDTO = weatherApiClient.getWeatherData(GANGNAM_LATITUDE, GANGNAM_LONGITUDE);
            saveWeatherToDatabase(LocalDate.now(), weatherDTO);
            log.info("날짜: {}에 대한 날씨 데이터가 저장되었습니다.", LocalDate.now());
        } catch (Exception e) {
            log.error("날씨 데이터를 저장하는 중 오류가 발생했습니다: {}", e.getMessage(), e);
        }
    }

    /**
     * 특정 날짜의 날씨 데이터를 조회합니다.
     * 데이터베이스에 없으면 API를 호출해 데이터를 가져옵니다.
     *
     * @param date 조회할 날짜
     * @return DateWeather 객체
     */
    public DateWeather getDateWeather(LocalDate date) {
        log.info("날짜: {}에 대한 날씨 데이터를 조회합니다.", date);
        return dateWeatherRepository.findById(date).orElseGet(() -> {
            log.info("날짜: {}에 대한 데이터베이스에 날씨 데이터가 없습니다. API에서 가져옵니다.", date);
            try {
                WeatherDTO weatherDTO = weatherApiClient.getWeatherData(GANGNAM_LATITUDE, GANGNAM_LONGITUDE);
                return saveWeatherToDatabase(date, weatherDTO);
            } catch (Exception e) {
                log.error("API에서 날씨 데이터를 가져오는 중 오류가 발생했습니다: {}", e.getMessage(), e);
                throw new RuntimeException("날씨 데이터를 가져오는 중 문제가 발생했습니다. 관리자에게 문의하세요.", e);
            }
        });
    }

    /**
     * 날씨 데이터를 데이터베이스에 저장합니다.
     *
     * @param date 저장할 날짜
     * @param weatherDTO 저장할 날씨 데이터
     * @return 저장된 DateWeather 객체
     */
    @Transactional
    protected DateWeather saveWeatherToDatabase(LocalDate date, WeatherDTO weatherDTO) {
        DateWeather dateWeather = new DateWeather();
        dateWeather.setDate(date);
        dateWeather.setWeather(weatherDTO.getWeather());
        dateWeather.setIcon(weatherDTO.getIcon());
        dateWeather.setTemperature(weatherDTO.getTemperature());
        dateWeatherRepository.save(dateWeather);
        log.info("날짜: {}에 대한 날씨 데이터가 데이터베이스에 저장되었습니다.", date);
        return dateWeather;
    }
}
