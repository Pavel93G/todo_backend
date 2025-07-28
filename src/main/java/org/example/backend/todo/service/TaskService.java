package org.example.backend.todo.service;

import org.example.backend.todo.entity.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.Date;
import java.util.List;

public interface TaskService {

    List<Task> findAll(String email);

    Task findById(Long id);

    Task add(Task task);

    Task update(Task task);

    void deleteById(Long id);

    Page<Task> findByParam(String title,
                           Boolean completed,
                           Long priorityId,
                           Long categoryId,
                           String email,
                           Date dateFrom,
                           Date dateTo,
                           PageRequest pageRequest);
}
