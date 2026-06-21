package com.example.weather.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.weather.dto.Weather;
import com.example.weather.repository.WeatherRepository;

@Service
public class WeatherService {

    private final WeatherRepository weatherRepository;

    public WeatherService(WeatherRepository weatherRepository) {
        this.weatherRepository = weatherRepository;
    }

    public List<Weather> findAll() {
        return weatherRepository.findAll();
    }

    public Weather findById(int id) {
        return weatherRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public Weather findByCoordinates(double latitude, double longitude) {
        return weatherRepository.findByLatitudeAndLongitude(latitude, longitude)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public Weather create(Weather weather) {
        return weatherRepository.save(weather);
    }

    public Weather update(int id, Weather weather) {
        Weather existing = findById(id);
        existing.setLatitude(weather.getLatitude());
        existing.setLongitude(weather.getLongitude());
        existing.setTemperature(weather.getTemperature());
        existing.setDescription(weather.getDescription());
        return weatherRepository.save(existing);
    }

    public void delete(int id) {
        if (!weatherRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        weatherRepository.deleteById(id);
    }
}
