# Weather Diary Project

날씨 데이터와 함께 일기를 작성하고 관리하는 REST API 서비스

## 주요 기능
- **일기 작성**: 날씨 데이터를 포함하여 일기 생성
- **일기 조회**: 특정 날짜 또는 기간별 일기 조회
- **일기 수정**: 기존 일기의 내용 수정
- **일기 삭제**: 특정 날짜의 일기 삭제
- **날씨 데이터 관리**: OpenWeatherMap API 연동, 매일 새벽 자동 수집, Redis 캐싱

## 개발 환경
- **Java**: 17
- **Gradle**: 8.11.1
- **Spring Boot**: 3.4.1

### 주요 의존성
- **Spring Boot Starter**
    - Web
    - Data JPA
    - Validation
    - JDBC
    - Data Redis
- **API 문서화**: SpringDoc OpenAPI (Swagger UI) 2.7.0
- **로깅**: SLF4J, Logback
- **테스트**: JUnit Jupiter, Mockito Core, Embedded Redis

### 데이터베이스
- **MySQL**: 운영 DB
- **Redis**: 날씨 데이터 캐싱
- **H2 Database**: 테스트 환경용

## 실행 및 API 문서
1. **설정**: `application.yml`에서 데이터베이스 및 Redis 설정
2. **환경 준비**
    - MySQL 서버 실행
    - Redis 서버 실행
3. **애플리케이션 실행**
4. **API 문서 확인**
    - Swagger UI: [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)
    - API Docs: [http://localhost:8080/v3/api-docs](http://localhost:8080/v3/api-docs)

## 프로젝트 구조
```text
src
├── main
│   ├── java
│   │   └── com.example.demo
│   │       ├── controller  // API 요청 처리
│   │       ├── service     // 비즈니스 로직
│   │       ├── repository  // 데이터베이스 접근
│   │       ├── domain      // 엔티티 클래스
│   │       └── config      // 설정 관련
│   └── resources
│       └── application.yml  // 환경 설정 파일
└── test
    └── java
