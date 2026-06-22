package com.example.person.service;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import com.example.person.model.Location;
import com.example.person.model.Person;
import com.example.person.repository.PersonRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PersonService {

    private final PersonRepository personRepository;
    private final RestTemplate restTemplate;

    @Value("${location.service.url}")
    private String locationServiceUrl;

    public List<Person> findAll() {
        return personRepository.findAll();
    }

    public Person findById(int id) {
        return personRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public Person create(Person person) {
        return personRepository.save(person);
    }

    public Person update(int id, Person person) {
        Person existing = findById(id);
        existing.setFirstname(person.getFirstname());
        existing.setSurname(person.getSurname());
        existing.setLastname(person.getLastname());
        existing.setBirthday(person.getBirthday());
        existing.setLocationName(person.getLocationName());
        return personRepository.save(existing);
    }

    public void delete(int id) {
        if (!personRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        personRepository.deleteById(id);
    }

    public Location getLocation(String location) {
        Person person = personRepository.findByLocationName(location)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        String url = locationServiceUrl + "/"
                + URLEncoder.encode(person.getLocationName(), StandardCharsets.UTF_8);

        return restTemplate.getForObject(url, Location.class);
    }
}
