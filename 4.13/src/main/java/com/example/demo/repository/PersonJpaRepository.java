package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.dto.Person;

interface PersonJpaRepository extends JpaRepository<Person, Integer> {
}
