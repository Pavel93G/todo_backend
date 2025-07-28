package org.example.backend.todo.service.impl;

import org.example.backend.todo.entity.Stat;
import org.example.backend.todo.repository.StatRepository;
import org.example.backend.todo.service.StatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StatServiceImpl implements StatService {

    private final StatRepository statRepository;

    @Autowired
    public StatServiceImpl(StatRepository statRepository) {
        this.statRepository = statRepository;
    }

    @Override
    public Stat findStat(String email) {
        return statRepository.findByUserEmail(email);
    }
}
