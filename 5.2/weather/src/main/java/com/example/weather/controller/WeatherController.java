package com.example.weather.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.weather.dto.Weather;
import com.example.weather.service.WeatherService;

@RestController
@RequestMapping("/weather")
public class WeatherController {

    private final WeatherService weatherService;

    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @GetMapping
    public List<Weather> getAll() {
        return weatherService.findAll();
    }

    @GetMapping(params = {"latitude", "longitude"})
    public Weather getByCoordinates(
            @RequestParam double latitude,
            @RequestParam double longitude) {
        return weatherService.findByCoordinates(latitude, longitude);
    }

    @GetMapping("/{id}")
    public Weather getById(@PathVariable int id) {
        return weatherService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Weather create(@RequestBody Weather weather) {
        return weatherService.create(weather);
    }

    @PutMapping("/{id}")
    public Weather update(@PathVariable int id, @RequestBody Weather weather) {
        return weatherService.update(id, weather);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        weatherService.delete(id);
    }
}
