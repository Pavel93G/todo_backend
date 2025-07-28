package org.example.backend.todo.repository;

import org.example.backend.todo.entity.Stat;
import org.springframework.data.repository.CrudRepository;

public interface StatRepository extends CrudRepository<Stat, Long> {

    Stat findByUserEmail(String email);
}
