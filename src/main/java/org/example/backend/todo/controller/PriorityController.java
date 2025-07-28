package org.example.backend.todo.controller;

import org.example.backend.todo.dto.search.PrioritySearchValues;
import org.example.backend.todo.entity.Priority;
import org.example.backend.todo.service.PriorityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("api/v1/priority")
public class PriorityController {

    private final PriorityService priorityService;

    @Autowired
    public PriorityController(PriorityService priorityService) {
        this.priorityService = priorityService;
    }

    @PostMapping("/all")
    public ResponseEntity<List<Priority>> findAllByEmail(@RequestBody String email) {
        var empty = priorityService.findAll(email);
        return ResponseEntity.ok(empty);
    }

    @PostMapping("/id")
    public ResponseEntity<Priority> findById(@RequestBody Long id) {
        Priority priority = null;

        try {
            priority = priorityService.findById(id);
        } catch (NoSuchElementException e) {
            e.printStackTrace();

            return new ResponseEntity("id=" + id + " не найдено!", HttpStatus.NOT_ACCEPTABLE);
        }

        return ResponseEntity.ok(priority);
    }

    @PostMapping("/add")
    public ResponseEntity<Priority> addPriority(@RequestBody Priority newPriority) {
        if (newPriority.getId() != null && newPriority.getId() != 0) {
            return new ResponseEntity("request param: id MUST be null", HttpStatus.NOT_ACCEPTABLE);
        }

        if (newPriority.getTitle() == null || newPriority.getTitle().trim().isEmpty()) {
            return new ResponseEntity("request param: title MUST be not null", HttpStatus.NOT_ACCEPTABLE);
        }

        if (newPriority.getColor() == null || newPriority.getColor().trim().isEmpty()) {
            return new ResponseEntity("request param: color MUST be not null", HttpStatus.NOT_ACCEPTABLE);
        }

        return ResponseEntity.ok(priorityService.add(newPriority));
    }

    @PutMapping("/update")
    public ResponseEntity update(@RequestBody Priority updatePriority) {
        if(updatePriority.getId() == null && updatePriority.getId() == 0) {
            return new ResponseEntity("requset param: id MUST be null", HttpStatus.NOT_ACCEPTABLE);
        }

        if (updatePriority.getTitle() == null || updatePriority.getTitle().trim().length() == 0) {
            return new ResponseEntity("request param: title MUST be null", HttpStatus.NOT_ACCEPTABLE);
        }

        if (updatePriority.getColor() == null || updatePriority.getColor().trim().length() == 0) {
            return new ResponseEntity("request color: title MUST be null", HttpStatus.NOT_ACCEPTABLE);
        }

        priorityService.update(updatePriority.getId(), updatePriority);

        return new ResponseEntity(HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        try {
            priorityService.deleteById(id);
        } catch (EmptyResultDataAccessException exception) {
            exception.printStackTrace();
            return new ResponseEntity("id=" + id + " not found", HttpStatus.NOT_ACCEPTABLE);
        }

        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping("/search")
    public ResponseEntity<List<Priority>> search(@RequestBody PrioritySearchValues prioritySearchValues) {

        if (prioritySearchValues.getTitle() == null ||
            prioritySearchValues.getEmail() == null ||
            prioritySearchValues.getEmail().trim().isEmpty()) {

            return new ResponseEntity("missing param: color", HttpStatus.NOT_ACCEPTABLE);
        }

        var byTitle = priorityService
                .findByTitle(prioritySearchValues.getTitle(), prioritySearchValues.getEmail());

        return ResponseEntity.ok(byTitle);
    }
}
