package com.example.demo.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.example.demo.dto.Message;

@Repository
public class MessageRepository {

    private final List<Message> messages = new ArrayList<>();
    private int nextId = 1;

    public List<Message> findAll() {
        return messages;
    }

    public Optional<Message> findById(int id) {
        return messages.stream()
                .filter(message -> message.getId() == id)
                .findFirst();
    }

    public Message save(Message message) {
        message.setId(nextId++);
        messages.add(message);
        return message;
    }

    public Optional<Message> update(int id, Message message) {
        return findById(id).map(existing -> {
            existing.setTitle(message.getTitle());
            existing.setText(message.getText());
            existing.setTime(message.getTime());
            return existing;
        });
    }

    public boolean deleteById(int id) {
        return messages.removeIf(message -> message.getId() == id);
    }
}
