package org.example.backend.todo.controller;

import org.example.backend.todo.dto.search.CategorySearchValues;
import org.example.backend.todo.entity.Category;
import org.example.backend.todo.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("api/v1/category")
public class CategoryController {
    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }


    @PostMapping("/id")
    public ResponseEntity<Category> findById(@RequestBody Long id) {
        Category category = null;

        try {
            category = categoryService.findById(id);
        } catch (NoSuchElementException e) {
            e.printStackTrace();

            return new ResponseEntity("id=" + id + " не найдено!", HttpStatus.NOT_ACCEPTABLE);
        }

        return ResponseEntity.ok(category);
    }

    @PostMapping("/add")
    public ResponseEntity<Category> addCategory(@RequestBody Category category) {

        if (category.getId() != null && category.getId() != 0) {
            return new ResponseEntity("request param: id MUST be null", HttpStatus.NOT_ACCEPTABLE);
        }

        if (category.getTitle() == null || category.getTitle().trim().isEmpty()) {
            return new ResponseEntity("request param: title MUST be not null", HttpStatus.NOT_ACCEPTABLE);
        }

        return ResponseEntity.ok(categoryService.add(category));
    }

    @PutMapping("/update")
    public ResponseEntity update(@RequestBody Category category) {
        if(category.getId() == null || category.getId() == 0) {
            return new ResponseEntity("requset param: id MUST be null", HttpStatus.NOT_ACCEPTABLE);
        }

        if (category.getTitle() == null || category.getTitle().trim().length() == 0) {
            return new ResponseEntity("request param: title MUST be null", HttpStatus.NOT_ACCEPTABLE);
        }

        categoryService.update(category);

        return new ResponseEntity(HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        try {
            categoryService.deleteById(id);
        } catch (EmptyResultDataAccessException exception) {
            exception.printStackTrace();
            return new ResponseEntity("id=" + id + " not found", HttpStatus.NOT_ACCEPTABLE);
        }

        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping("/search")
    public ResponseEntity<List<Category>> search(@RequestBody CategorySearchValues categorySearchValues) {

        if (categorySearchValues.getEmail() == null || categorySearchValues.getEmail().trim().length() == 0) {
            return new ResponseEntity("missing param: email ", HttpStatus.NOT_ACCEPTABLE);
        }

        var byTitle = categoryService
                .findByTitle(categorySearchValues.getTitle(), categorySearchValues.getEmail());

        return ResponseEntity.ok(byTitle);
    }
}
