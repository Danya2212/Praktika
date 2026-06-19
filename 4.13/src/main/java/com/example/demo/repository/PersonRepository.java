package com.example.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.example.demo.dto.Person;

@Repository
public class PersonRepository {

    private final PersonJpaRepository jpaRepository;

    public PersonRepository(PersonJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    public List<Person> findAll() {
        return jpaRepository.findAll();
    }

    public Optional<Person> findById(int id) {
        return jpaRepository.findById(id);
    }

    public Person save(Person person) {
        return jpaRepository.save(person);
    }

    public boolean existsById(int id) {
        return jpaRepository.existsById(id);
    }

    public void deleteById(int id) {
        jpaRepository.deleteById(id);
    }
}
