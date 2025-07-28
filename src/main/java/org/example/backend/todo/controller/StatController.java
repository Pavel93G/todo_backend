package org.example.backend.todo.controller;

import org.example.backend.todo.entity.Stat;
import org.example.backend.todo.service.StatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/")
public class StatController {

    private final StatService statService;

    @Autowired
    public StatController(StatService statService) {
        this.statService = statService;
    }

    @PostMapping("/stat")
    public ResponseEntity<Stat> findByEmail(@RequestBody String email) {

        return ResponseEntity.ok(statService.findStat(email));
    }
}
