package com.example.person.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.person.model.Person;

public interface PersonRepository extends JpaRepository<Person, Integer> {
}
