package com.example.weather.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.example.weather.model.Root;

@Service
public class WeatherService {

    private final RestTemplate restTemplate;
    private final String apiUrl;
    private final String apiKey;

    public WeatherService(
            RestTemplate restTemplate,
            @Value("${weather.api.url}") String apiUrl,
            @Value("${weather.api.key}") String apiKey) {
        this.restTemplate = restTemplate;
        this.apiUrl = apiUrl;
        this.apiKey = apiKey;
    }

    @Cacheable(value = "weather", key = "#lat + '_' + #lon")
    public Root getWeather(double lat, double lon) {
        String url = UriComponentsBuilder.fromHttpUrl(apiUrl)
                .queryParam("lat", lat)
                .queryParam("lon", lon)
                .queryParam("appid", apiKey)
                .queryParam("units", "metric")
                .toUriString();

        return restTemplate.getForObject(url, Root.class);
    }
}
