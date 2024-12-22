package com.example.demo.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;
import com.example.demo.domain.Diary;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
@TestPropertySource(properties = {
    "openweather.api-key=5d434267d672e84f5d7a7b1c4a9d6b11",
    "openweather.base-url=https://api.openweathermap.org/data/2.5"
})
class WeatherRepositoryTest {

    @Autowired
    private DiaryRepository diaryRepository;

    @Test
    void saveDiaryTest() {
        // given
        Diary diary = createDiary(LocalDate.now(), "흐림", "01d", 20.5, "배가 고파서 배달의 민족에서 떡볶이를 시켰다.");

        // when
        Diary savedDiary = diaryRepository.save(diary);

        // then
        assertNotNull(savedDiary);
        assertEquals(diary.getWeather(), savedDiary.getWeather());
        assertEquals(diary.getText(), savedDiary.getText());
        assertEquals(diary.getDate(), savedDiary.getDate());
        assertEquals(diary.getIcon(), savedDiary.getIcon());
        assertEquals(diary.getTemperature(), savedDiary.getTemperature());
    }

    @Test
    void findDiaryByDateTest() {
        // given
        LocalDate date = LocalDate.now();
        Diary diary = createDiary(date, "맑음", "02d", 25.0, "오늘의 일기");
        diaryRepository.save(diary);

        // when
        List<Diary> diaryList = diaryRepository.findAllByDate(date);

        // then
        assertFalse(diaryList.isEmpty());
        assertEquals(1, diaryList.size());
        Diary foundDiary = diaryList.get(0);
        assertEquals("오늘의 일기", foundDiary.getText());
        assertEquals("맑음", foundDiary.getWeather());
        assertEquals(date, foundDiary.getDate());
    }

    @Test
    void findDiaryBetweenDatesTest() {
        // given
        LocalDate startDate = LocalDate.now().minusDays(1);
        LocalDate endDate = LocalDate.now();

        Diary diary1 = createDiary(startDate, "흐림", "03d", 18.5, "어제의 일기");
        Diary diary2 = createDiary(endDate, "맑음", "01d", 22.0, "오늘의 일기");

        diaryRepository.save(diary1);
        diaryRepository.save(diary2);

        // when
        List<Diary> diaries = diaryRepository.findAllByDateBetween(startDate, endDate);

        // then
        assertEquals(2, diaries.size());
        assertTrue(diaries.stream().anyMatch(d -> d.getText().equals("어제의 일기")));
        assertTrue(diaries.stream().anyMatch(d -> d.getText().equals("오늘의 일기")));
    }

    private Diary createDiary(LocalDate date, String weather, String icon, double temperature, String text) {
        Diary diary = new Diary();
        diary.setDate(date);
        diary.setWeather(weather);
        diary.setIcon(icon);
        diary.setTemperature(temperature);
        diary.setText(text);
        return diary;
    }
}