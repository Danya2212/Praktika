package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.dto.Message;

interface MessageJpaRepository extends JpaRepository<Message, Integer> {
}
