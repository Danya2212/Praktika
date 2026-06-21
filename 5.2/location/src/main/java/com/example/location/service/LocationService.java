package com.example.location.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.location.dto.Location;
import com.example.location.repository.LocationRepository;

@Service
public class LocationService {

    private final LocationRepository locationRepository;

    public LocationService(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    public List<Location> findAll() {
        return locationRepository.findAll();
    }

    public Location findById(int id) {
        return locationRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public Location findByPersonId(int personId) {
        return locationRepository.findByPersonId(personId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public Location create(Location location) {
        return locationRepository.save(location);
    }

    public Location update(int id, Location location) {
        Location existing = findById(id);
        existing.setPersonId(location.getPersonId());
        existing.setCity(location.getCity());
        existing.setLatitude(location.getLatitude());
        existing.setLongitude(location.getLongitude());
        return locationRepository.save(existing);
    }

    public void delete(int id) {
        if (!locationRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        locationRepository.deleteById(id);
    }
}
