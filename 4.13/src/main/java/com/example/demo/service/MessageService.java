package com.example.demo.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.demo.dto.Message;
import com.example.demo.repository.MessageRepository;

@Service
public class MessageService {

    private final MessageRepository messageRepository;

    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public List<Message> findAll() {
        return messageRepository.findAll();
    }

    public Message findById(int id) {
        return messageRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public Message create(Message message) {
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
