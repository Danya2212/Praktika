package com.example.location.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import com.example.location.model.Location;
import com.example.location.model.Weather;
import com.example.location.dto.WeatherApiResponse;
import com.example.location.repository.LocationRepository;

@Service
public class LocationService {

    private final LocationRepository repository;
    private final RestTemplate restTemplate;
    private final String weatherServiceUrl;

    public LocationService(
            LocationRepository repository,
            RestTemplate restTemplate,
            @Value("${weather.service.url}") String weatherServiceUrl) {
        this.repository = repository;
        this.restTemplate = restTemplate;
        this.weatherServiceUrl = weatherServiceUrl;
    }

    public List<Location> findAll() {
        return repository.findAll();
    }

    public Location findByName(String name) {
        return repository.findByName(name)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public Location create(Location location) {
        return repository.save(location);
    }

    public Location update(String name, Location location) {
        Location existing = findByName(name);

        existing.setLongitude(location.getLongitude());
        existing.setLatitude(location.getLatitude());
        if (location.getName() != null && !location.getName().equals(name)) {
            repository.delete(existing);
            existing.setName(location.getName());
        }
        return repository.save(existing);
    }

    public void delete(String name) {
        Location existing = findByName(name);
        repository.delete(existing);
    }

    public Weather getWeather(String name) {
        Location geodata = findByName(name);

        String url = UriComponentsBuilder.fromHttpUrl(weatherServiceUrl)
                .queryParam("lat", geodata.getLatitude())
                .queryParam("lon", geodata.getLongitude())
                .toUriString();

        WeatherApiResponse response = restTemplate.getForObject(url, WeatherApiResponse.class);

        Weather weather = new Weather();
        weather.setLatitude(geodata.getLatitude());
        weather.setLongitude(geodata.getLongitude());
        if (response != null && response.getMain() != null) {
            weather.setTemperature(response.getMain().getTemp());
        }
        if (response != null && response.getWeather() != null && !response.getWeather().isEmpty()) {
            weather.setDescription(response.getWeather().get(0).getDescription());
        }
        return weather;
    }
}
