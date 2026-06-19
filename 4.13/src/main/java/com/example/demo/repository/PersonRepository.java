package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.dto.Person;

public interface PersonRepository extends JpaRepository<Person, Integer> {
}
