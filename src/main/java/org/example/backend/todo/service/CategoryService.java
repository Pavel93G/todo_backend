package org.example.backend.todo.service;

import org.example.backend.todo.entity.Category;

import java.util.List;

public interface CategoryService {

    Category findById(Long id);

    List<Category> findAll(String email);

    Category add(Category category);

    Category update(Category category);

    void deleteById(Long id);

    List<Category> findByTitle(String title, String email);
}
