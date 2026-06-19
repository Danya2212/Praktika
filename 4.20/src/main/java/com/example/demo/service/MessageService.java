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
public class MessageService {

    private final MessageRepository messageRepository;
    private final PersonRepository personRepository;

    public MessageService(MessageRepository messageRepository, PersonRepository personRepository) {
        this.messageRepository = messageRepository;
        this.personRepository = personRepository;
    }

    public List<Message> findAll() {
        return messageRepository.findAll();
    }

    public Message findById(int id) {
        return messageRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public Message create(Message message) {
        if (message.getPerson() == null || message.getPerson().getId() == 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        int personId = message.getPerson().getId();
        Person person = personRepository.findById(personId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));

        message.setPerson(person);
        return messageRepository.save(message);
    }

    public Message update(int id, Message message) {
        Message existing = findById(id);
        existing.setTitle(message.getTitle());
        existing.setText(message.getText());
        existing.setTime(message.getTime());
        return messageRepository.save(existing);
    }

    public void delete(int id) {
        if (!messageRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        messageRepository.deleteById(id);
    }
}
