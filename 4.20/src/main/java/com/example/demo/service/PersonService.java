package com.example.demo.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.demo.dto.Message;
import com.example.demo.dto.Person;
import com.example.demo.repository.MessageRepository;
import com.example.demo.repository.PersonRepository;

@Service
public class PersonService {

    private final PersonRepository personRepository;
    private final MessageRepository messageRepository;

    public PersonService(PersonRepository personRepository, MessageRepository messageRepository) {
        this.personRepository = personRepository;
        this.messageRepository = messageRepository;
    }

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
        return personRepository.save(existing);
    }

    public void delete(int id) {
        if (!personRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        personRepository.deleteById(id);
    }

    public List<Message> getMessages(int personId) {
        findById(personId);
        return messageRepository.findByPersonId(personId);
    }

    public Message getMessage(int personId, int messageId) {
        findById(personId);
        return messageRepository.findByIdAndPersonId(messageId, personId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public Message addMessage(int personId, Message message) {
        Person person = personRepository.findById(personId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));

        message.setPerson(person);
        return messageRepository.save(message);
    }

    public void deleteMessage(int personId, int messageId) {
        findById(personId);
        Message message = messageRepository.findByIdAndPersonId(messageId, personId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        messageRepository.delete(message);
    }
}
