package com.example.person.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import com.example.person.model.Person;
import com.example.person.model.Weather;
import com.example.person.repository.PersonRepository;

@Service
public class PersonService {

    private final PersonRepository repository;
    private final RestTemplate restTemplate;
    private final String locationServiceUrl;

    public PersonService(
            PersonRepository repository,
            RestTemplate restTemplate,
            @Value("${location.service.url}") String locationServiceUrl) {
        this.repository = repository;
        this.restTemplate = restTemplate;
        this.locationServiceUrl = locationServiceUrl;
    }

    public List<Person> findAll() {
        return repository.findAll();
    }

    public Person findById(int id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public Person create(Person person) {
        return repository.save(person);
    }

    public Person update(int id, Person person) {
        Person existing = findById(id);

        existing.setFirstname(person.getFirstname());
        existing.setSurname(person.getSurname());
        existing.setLastname(person.getLastname());
        existing.setBirthday(person.getBirthday());
        existing.setLocation(person.getLocation());
        return repository.save(existing);
    }

    public void delete(int id) {
        if (!repository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        repository.deleteById(id);
    }

    public Weather getWeather(int id) {
        Person person = findById(id);

        String url = UriComponentsBuilder.fromHttpUrl(locationServiceUrl)
                .queryParam("name", person.getLocation())
                .toUriString();

        return restTemplate.getForObject(url, Weather.class);
    }
}
