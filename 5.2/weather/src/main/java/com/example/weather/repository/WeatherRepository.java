package com.example.weather.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.weather.dto.Weather;

public interface WeatherRepository extends JpaRepository<Weather, Integer> {

    Optional<Weather> findByLatitudeAndLongitude(double latitude, double longitude);
}
