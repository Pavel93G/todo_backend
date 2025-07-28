package org.example.backend.todo.service.impl;

import org.example.backend.todo.entity.Category;
import org.example.backend.todo.repository.CategoryRepository;
import org.example.backend.todo.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Transactional(readOnly = true)
    @Override
    public Category findById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Категория не найдена"));
    }

    @Transactional(readOnly = true)
    @Override
    public List<Category> findAll(String email) {
        return categoryRepository.findByUserEmailOrderByTitleAsc(email);
    }

    @Transactional
    @Override
    public Category add(Category category) {
        return categoryRepository.save(category);
    }

    @Transactional
    @Override
    public Category update(Category category) {
        return categoryRepository.save(category);
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        categoryRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Category> findByTitle(String title, String email) {
        return categoryRepository.findByTitle(title, email);
    }
}
