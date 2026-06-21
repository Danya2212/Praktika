package com.example.person.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import com.example.person.model.Person;
import com.example.person.model.Weather;
import com.example.person.repository.PersonRepository;

@RestController
@RequestMapping("/person")
public class PersonController {

    private final PersonRepository repository;
    private final RestTemplate restTemplate;
    private final String locationServiceUrl;

    public PersonController(
            PersonRepository repository,
            RestTemplate restTemplate,
            @Value("${location.service.url}") String locationServiceUrl) {
        this.repository = repository;
        this.restTemplate = restTemplate;
        this.locationServiceUrl = locationServiceUrl;
    }

    @GetMapping
    public List<Person> getAll() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public Person getById(@PathVariable int id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Person create(@RequestBody Person person) {
        return repository.save(person);
    }

    @PutMapping("/{id}")
    public Person update(@PathVariable int id, @RequestBody Person person) {
        Person existing = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        existing.setFirstname(person.getFirstname());
        existing.setSurname(person.getSurname());
        existing.setLastname(person.getLastname());
        existing.setBirthday(person.getBirthday());
        existing.setLocation(person.getLocation());
        return repository.save(existing);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        if (!repository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        repository.deleteById(id);
    }

    @GetMapping("/{id}/weather")
    public Weather getWeather(@PathVariable int id) {
        Person person = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        String url = UriComponentsBuilder.fromHttpUrl(locationServiceUrl)
                .queryParam("name", person.getLocation())
                .toUriString();

        return restTemplate.getForObject(url, Weather.class);
    }
}
