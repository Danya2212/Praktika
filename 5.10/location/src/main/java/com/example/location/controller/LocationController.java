package com.example.location.controller;

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

import com.example.location.model.Location;
import com.example.location.model.Weather;
import com.example.location.service.LocationService;

@RestController
@RequestMapping("/location")
public class LocationController {

    private final LocationService locationService;

    public LocationController(LocationService locationService) {
        this.locationService = locationService;
    }

    @GetMapping
    public Object getLocations(@RequestParam(required = false) String name) {
        if (name != null) {
            return locationService.findByName(name);
        }
        return locationService.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Location create(@RequestBody Location location) {
        return locationService.create(location);
    }

    @PutMapping
    public Location update(@RequestParam String name, @RequestBody Location location) {
        return locationService.update(name, location);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@RequestParam String name) {
        locationService.delete(name);
    }

    @GetMapping("/weather")
    public Weather getWeather(@RequestParam String name) {
        return locationService.getWeather(name);
    }
}
