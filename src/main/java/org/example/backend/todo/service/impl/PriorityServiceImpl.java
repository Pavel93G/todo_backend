package org.example.backend.todo.service.impl;

import org.example.backend.todo.entity.Priority;
import org.example.backend.todo.repository.PriorityRepository;
import org.example.backend.todo.service.PriorityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class PriorityServiceImpl implements PriorityService {

    private final PriorityRepository priorityRepository;

    @Autowired
    public PriorityServiceImpl(PriorityRepository priorityRepository) {
        this.priorityRepository = priorityRepository;
    }

    @Transactional(readOnly = true)
    @Override
    public Priority findById(Long id) {
        return priorityRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Приоритет не найдена"));
    }

    @Transactional(readOnly = true)
    @Override
    public List<Priority> findAll(String email) {
        return priorityRepository.findByUserEmailOrderByIdAsc(email);
    }

    @Transactional()
    @Override
    public Priority add(Priority category) {
        return priorityRepository.save(category);
    }

    @Transactional
    @Override
    public Priority update(Long id, Priority priority) {
        var updatePriority = priorityRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Приоритет не найдена"));

        updatePriority.setTitle(priority.getTitle());
        updatePriority.setColor(priority.getColor());

        return priorityRepository.save(updatePriority);
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        priorityRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Priority> findByTitle(String title, String email) {

        return priorityRepository.findByTitle(title, email);
    }
}
