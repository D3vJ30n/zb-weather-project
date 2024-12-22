package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test") // 테스트 프로파일 활성화
class WeatherApplicationTests {

    @Test
    void contextLoads() {
        // 애플리케이션 컨텍스트가 성공적으로 로드되면 통과
    }
}
