package com.example.location.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.location.model.Location;

public interface LocationRepository extends JpaRepository<Location, String> {

    Optional<Location> findByName(String name);
}
