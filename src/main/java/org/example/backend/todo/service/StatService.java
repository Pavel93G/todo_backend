package org.example.backend.todo.service;

import org.example.backend.todo.entity.Priority;
import org.example.backend.todo.entity.Stat;

import java.util.List;

public interface StatService {

    Stat findStat(String email);
}
