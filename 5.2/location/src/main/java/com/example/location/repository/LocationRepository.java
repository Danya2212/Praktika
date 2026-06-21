package com.example.location.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.location.dto.Location;

public interface LocationRepository extends JpaRepository<Location, Integer> {

    Optional<Location> findByPersonId(int personId);

    List<Location> findAllByPersonId(int personId);
}
