package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
import org.springframework.web.server.ResponseStatusException;

import com.example.demo.dto.Message;

@RestController
@RequestMapping("/message")
public class MessageController {

    private final List<Message> messages = new ArrayList<>();
    private int nextId = 1;

    @GetMapping
    public List<Message> getAll() {
        return messages;
    }

    @GetMapping("/{id}")
    public Message getById(@PathVariable int id) {
        return findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Message create(@RequestBody Message message) {
        message.setId(nextId++);
        messages.add(message);
        return message;
    }

    @PutMapping("/{id}")
    public Message update(@PathVariable int id, @RequestBody Message message) {
        Message existing = findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        existing.setTitle(message.getTitle());
        existing.setText(message.getText());
        existing.setTime(message.getTime());
        return existing;
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        if (!messages.removeIf(message -> message.getId() == id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    private Optional<Message> findById(int id) {
        return messages.stream()
                .filter(message -> message.getId() == id)
                .findFirst();
    }
}
