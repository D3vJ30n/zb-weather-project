package com.example.demo.util;

import com.example.demo.dto.WeatherDTO;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@Component
public class WeatherApiClient {

    @Value("${openweather.api-key}")
    private String apiKey;

    @Value("${openweather.base-url}")
    private String baseUrl;

    public String getWeatherString(double latitude, double longitude) {
        try {
            // URL 생성
            HttpURLConnection connection = createConnection(latitude, longitude);

            int responseCode = connection.getResponseCode();
            try (BufferedReader br = new BufferedReader(new InputStreamReader(
                responseCode == 200 ? connection.getInputStream() : connection.getErrorStream()))) {

                StringBuilder response = new StringBuilder();
                String inputLine;
                while ((inputLine = br.readLine()) != null) {
                    response.append(inputLine);
                }

                if (responseCode != 200) {
                    throw new RuntimeException("API 호출 실패: " + responseCode + " - " + response.toString());
                }

                return response.toString();
            }
        } catch (Exception e) {
            throw new RuntimeException("날씨 API 호출 중 오류가 발생했습니다.", e);
        }
    }

    private HttpURLConnection createConnection(double latitude, double longitude) throws Exception {
        // URL 템플릿을 사용하여 API URL 생성
        String apiUrl = String.format(baseUrl, latitude, longitude, apiKey);
        URL url = new URL(apiUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setConnectTimeout(5000);
        connection.setReadTimeout(5000);
        return connection;
    }

    private WeatherDTO parseWeatherData(String jsonString) {
        try {
            JSONObject weatherData = new JSONObject(jsonString);
            JSONObject mainData = weatherData.getJSONObject("main");
            JSONObject weatherJson = weatherData.getJSONArray("weather").getJSONObject(0);

            return WeatherDTO.builder()
                .weather(weatherJson.getString("main"))
                .icon(weatherJson.getString("icon"))
                .temperature(mainData.getDouble("temp"))
                .build();
        } catch (Exception e) {
            throw new RuntimeException("날씨 데이터 파싱 중 오류가 발생했습니다.", e);
        }
    }

    public WeatherDTO getWeatherData(double latitude, double longitude) {
        try {
            String jsonString = getWeatherString(latitude, longitude);
            return parseWeatherData(jsonString);
        } catch (Exception e) {
            throw new RuntimeException("날씨 데이터 조회 중 오류가 발생했습니다.", e);
        }
    }
}
