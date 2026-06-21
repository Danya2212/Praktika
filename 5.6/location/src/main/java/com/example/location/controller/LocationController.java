package com.example.location.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import com.example.location.model.Location;
import com.example.location.model.Weather;
import com.example.location.model.WeatherApiResponse;
import com.example.location.repository.LocationRepository;

@RestController
@RequestMapping("/location")
public class LocationController {

    private final LocationRepository repository;
    private final RestTemplate restTemplate;
    private final String weatherServiceUrl;

    public LocationController(
            LocationRepository repository,
            RestTemplate restTemplate,
            @Value("${weather.service.url}") String weatherServiceUrl) {
        this.repository = repository;
        this.restTemplate = restTemplate;
        this.weatherServiceUrl = weatherServiceUrl;
    }

    @GetMapping
    public Object getLocations(@RequestParam(required = false) String name) {
        if (name != null) {
            return repository.findByName(name)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        }
        return repository.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Location create(@RequestBody Location location) {
        return repository.save(location);
    }

    @PutMapping
    public Location update(@RequestParam String name, @RequestBody Location location) {
        Location existing = repository.findByName(name)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        existing.setLongitude(location.getLongitude());
        existing.setLatitude(location.getLatitude());
        if (location.getName() != null && !location.getName().equals(name)) {
            repository.delete(existing);
            existing.setName(location.getName());
        }
        return repository.save(existing);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@RequestParam String name) {
        Location existing = repository.findByName(name)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        repository.delete(existing);
    }

    @GetMapping("/weather")
    public Weather getWeather(@RequestParam String name) {
        Location geodata = repository.findByName(name)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

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
