package com.example.demo.util;

import java.time.LocalDate;

public class DateUtil {
    public static void validateDate(LocalDate date) {
        if (date == null || date.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("유효하지 않은 날짜입니다.");
        }
    }

    public static void validateDateRange(LocalDate startDate, LocalDate endDate) {
        if (startDate == null || endDate == null || startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("유효하지 않은 날짜 범위입니다.");
        }
    }
}
