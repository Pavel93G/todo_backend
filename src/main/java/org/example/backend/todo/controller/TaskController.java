package org.example.backend.todo.controller;

import lombok.extern.java.Log;
import org.example.backend.todo.dto.search.TaskSearchValues;
import org.example.backend.todo.entity.Task;
import org.example.backend.todo.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("api/v1/task")
@Log
public class TaskController {

    public static final String ID_COLUMN = "id";
    private final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping("/id")
    public ResponseEntity<Task> findById(@RequestBody Long id) {
        Task task = null;

        try {
            task = taskService.findById(id);
        } catch (NoSuchElementException e) {
            e.printStackTrace();
            return new ResponseEntity("id=" + id + " not found", HttpStatus.NOT_ACCEPTABLE);
        }

        return ResponseEntity.ok(task);
    }

    @PostMapping("/all")
    public ResponseEntity<List<Task>> findAllTask(@RequestBody String email) {
        return ResponseEntity.ok(taskService.findAll(email));
    }

    @PostMapping("/add")
    public ResponseEntity<Task> addTask(@RequestBody Task task) {
        System.out.println("Received task: " + task);
        if (task.getId() != null && task.getId() != 0) {
            return new ResponseEntity("request param: id MUST be null", HttpStatus.NOT_ACCEPTABLE);
        }

        if (task.getTitle() == null || task.getTitle().trim().isEmpty()) {
            return new ResponseEntity("request param: title MUST be not null", HttpStatus.NOT_ACCEPTABLE);
        }

        return ResponseEntity.ok(taskService.add(task));
    }

    @PutMapping("/update")
    public ResponseEntity<Task> updateTask(@RequestBody Task task) {
        if (task.getId() == null || task.getId() == 0) {
            return new ResponseEntity("missing param: id", HttpStatus.NOT_ACCEPTABLE);
        }

        if (task.getTitle() == null || task.getTitle().trim().isEmpty()) {
            return new ResponseEntity("missing param: title", HttpStatus.NOT_ACCEPTABLE);
        }
        taskService.update(task);

        return new ResponseEntity(HttpStatus.OK);
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity deleteTask(@PathVariable Long id) {

        try {
            taskService.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            e.printStackTrace();
            return new ResponseEntity("id=" + id + " not found", HttpStatus.NOT_ACCEPTABLE);
        }

        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping("/search")
    public ResponseEntity<Page<Task>> search(@RequestBody TaskSearchValues taskSearchValues) throws ParseException {
        log.info("Received search request: {}");
        var title = taskSearchValues.getTitle() != null ? taskSearchValues.getTitle() : null;

        var completed = taskSearchValues.getCompleted() != null && taskSearchValues.getCompleted() == 1 ? true : false;

        var priorityId = taskSearchValues.getPriorityId() != null ? taskSearchValues.getPriorityId() : null;
        var categoryId = taskSearchValues.getCategoryId() != null ? taskSearchValues.getCategoryId() : null;

        var sortColumn = taskSearchValues.getSortColumn() != null ? taskSearchValues.getSortColumn() : null;
        var sortDirection = taskSearchValues.getSortDirection() != null ? taskSearchValues.getSortDirection() : null;

        var pageNumber = taskSearchValues.getPageNumber() != null ? taskSearchValues.getPageNumber() : null;
        var pageSize = taskSearchValues.getPageSize() != null ? taskSearchValues.getPageSize() : null;

        var email = taskSearchValues.getEmail() != null ? taskSearchValues.getEmail() : null;

        if (email == null || email.trim().length() == 0) {
            return new ResponseEntity("missing param: email", HttpStatus.NOT_ACCEPTABLE);
        }

        Date dateFrom = null;
        Date dateTo = null;

        if (taskSearchValues.getDateFrom() != null) {
            Calendar calendarFrom = Calendar.getInstance();
            calendarFrom.setTime(taskSearchValues.getDateFrom());
            calendarFrom.set(Calendar.HOUR_OF_DAY, 0);
            calendarFrom.set(Calendar.MINUTE, 1);
            calendarFrom.set(Calendar.SECOND, 1);
            calendarFrom.set(Calendar.MILLISECOND, 1);

            dateFrom = calendarFrom.getTime();
        }

        if (taskSearchValues.getDateTo() != null) {
            Calendar calendarTo = Calendar.getInstance();
            calendarTo.setTime(taskSearchValues.getDateTo());
            calendarTo.set(Calendar.HOUR_OF_DAY, 23);
            calendarTo.set(Calendar.MINUTE, 59);
            calendarTo.set(Calendar.SECOND, 59);
            calendarTo.set(Calendar.MILLISECOND, 999);

            dateTo = calendarTo.getTime();
        }

        Sort.Direction direction = sortDirection == null || sortDirection.trim().length() == 0 ||
                                   sortDirection.trim().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;

        var sort = Sort.by(direction, sortColumn, ID_COLUMN);

        var pageRequest = PageRequest.of(pageNumber, pageSize, sort);

        var result = taskService.findByParam(title, completed, priorityId, categoryId, email, dateFrom,
                dateTo, pageRequest);

        return ResponseEntity.ok(result);
    }
}