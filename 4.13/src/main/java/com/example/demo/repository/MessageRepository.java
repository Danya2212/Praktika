package com.example.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.example.demo.dto.Message;

@Repository
public class MessageRepository {

    private final MessageJpaRepository jpaRepository;

    public MessageRepository(MessageJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    public List<Message> findAll() {
        return jpaRepository.findAll();
    }

    public Optional<Message> findById(int id) {
        return jpaRepository.findById(id);
    }

    public Message save(Message message) {
        return jpaRepository.save(message);
    }

    public boolean existsById(int id) {
        return jpaRepository.existsById(id);
    }

    public void deleteById(int id) {
        jpaRepository.deleteById(id);
    }
}
