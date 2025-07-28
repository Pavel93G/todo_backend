package org.example.backend.todo.service.impl;

import org.example.backend.todo.entity.Task;
import org.example.backend.todo.repository.TaskRepository;
import org.example.backend.todo.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;

    @Autowired
    public TaskServiceImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Transactional(readOnly = true)
    @Override
    public List<Task> findAll(String email) {
        return taskRepository.findByUserEmailOrderByTitleAsc(email);
    }

    @Transactional(readOnly = true)
    @Override
    public Task findById(Long id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task Not Found by id=" + id));
    }

    @Transactional
    @Override
    public Task add(Task task) {
        return taskRepository.save(task);
    }

    @Transactional
    @Override
    public Task update(Task task) {
        return taskRepository.save(task);
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        taskRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public Page<Task> findByParam(String title,
                                  Boolean completed,
                                  Long priorityId,
                                  Long categoryId,
                                  String email,
                                  Date dateFrom,
                                  Date dateTo,
                                  PageRequest pageRequest) {
        return taskRepository.findByParam(title, completed, priorityId, categoryId, email, dateFrom, dateTo, pageRequest);
    }
}
