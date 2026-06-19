package com.example.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.dto.Message;

public interface MessageRepository extends JpaRepository<Message, Integer> {

    List<Message> findByPersonId(int personId);

    Optional<Message> findByIdAndPersonId(int id, int personId);
}
