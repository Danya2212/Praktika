package com.example.demo.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.example.demo.dto.Person;

@Repository
public class PersonRepository {

    private final List<Person> persons = new ArrayList<>();
    private int nextId = 1;

    public List<Person> findAll() {
        return persons;
    }

    public Optional<Person> findById(int id) {
        return persons.stream()
                .filter(person -> person.getId() == id)
                .findFirst();
    }

    public Person save(Person person) {
        person.setId(nextId++);
        persons.add(person);
        return person;
    }

    public Optional<Person> update(int id, Person person) {
        return findById(id).map(existing -> {
            existing.setFirstname(person.getFirstname());
            existing.setSurname(person.getSurname());
            existing.setLastname(person.getLastname());
            existing.setBirthday(person.getBirthday());
            return existing;
        });
    }

    public boolean deleteById(int id) {
        return persons.removeIf(person -> person.getId() == id);
    }
}
