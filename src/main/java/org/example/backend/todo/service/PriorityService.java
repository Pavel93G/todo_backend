package org.example.backend.todo.service;

import org.example.backend.todo.entity.Priority;

import java.util.List;

public interface PriorityService {
    Priority findById(Long id);

    List<Priority> findAll(String email);

    Priority add(Priority category);

    Priority update(Long id, Priority priority);

    void deleteById(Long id);

    List<Priority> findByTitle(String title, String email);
}
