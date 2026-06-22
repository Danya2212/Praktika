package com.example.location.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WeatherApiResponse {

    private Main main;
    private List<WeatherItem> weather;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Main {
        private double temp;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class WeatherItem {
        private String description;
    }
}
