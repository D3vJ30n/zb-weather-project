package com.example.demo.controller;

import com.example.demo.dto.DiaryRequestDTO;
import com.example.demo.dto.DiaryResponseDTO;
import com.example.demo.service.DiaryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "날씨 일기 API", description = "날씨 일기 CRUD API")
public class DiaryController {
    private final DiaryService diaryService;

    @Operation(summary = "일기 생성", description = "날짜와 일기 내용을 입력하여 날씨 일기를 생성합니다.")
    @PostMapping("/create/diary")
    public ResponseEntity<Void> createDiary(
        @Parameter(description = "날짜", example = "2023-12-23")
        @RequestParam(name = "date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
        @Valid @RequestBody DiaryRequestDTO requestDTO) {
        diaryService.createDiary(date, requestDTO);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "일기 조회", description = "특정 날짜의 모든 일기를 조회합니다.")
    @GetMapping("/read/diary")
    public ResponseEntity<List<DiaryResponseDTO>> readDiary(
        @Parameter(description = "날짜", example = "2023-12-23")
        @RequestParam(name = "date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return ResponseEntity.ok(diaryService.readDiary(date));
    }

    @Operation(summary = "기간별 일기 조회", description = "특정 기간의 모든 일기를 조회합니다.")
    @GetMapping("/read/diaries")
    public ResponseEntity<List<DiaryResponseDTO>> readDiaries(
        @Parameter(description = "시작 날짜", example = "2023-12-01")
        @RequestParam(name = "startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
        @Parameter(description = "종료 날짜", example = "2023-12-23")
        @RequestParam(name = "endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        return ResponseEntity.ok(diaryService.readDiaries(startDate, endDate));
    }

    @Operation(summary = "일기 수정", description = "특정 날짜의 첫 번째 일기를 수정합니다.")
    @PutMapping("/update/diary")
    public ResponseEntity<Void> updateDiary(
        @Parameter(description = "날짜", example = "2023-12-23")
        @RequestParam(name = "date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
        @Valid @RequestBody DiaryRequestDTO requestDTO) {
        diaryService.updateDiary(date, requestDTO);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "일기 삭제", description = "특정 날짜의 모든 일기를 삭제합니다.")
    @DeleteMapping("/delete/diary")
    public ResponseEntity<Void> deleteDiary(
        @Parameter(description = "날짜", example = "2023-12-23")
        @RequestParam(name = "date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        diaryService.deleteDiary(date);
        return ResponseEntity.ok().build();
    }
}
