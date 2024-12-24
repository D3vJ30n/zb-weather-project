package com.example.demo.repository;

import com.example.demo.domain.DateWeather;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

public interface DateWeatherRepository extends JpaRepository<DateWeather, LocalDate> {
}