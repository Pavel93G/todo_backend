package org.example.backend.todo.repository;

import org.example.backend.todo.entity.Priority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PriorityRepository extends JpaRepository<Priority,Long> {

    List<Priority> findByUserEmailOrderByIdAsc(String email);

    @Query("""
            SELECT p FROM Priority p
            WHERE (:title is null or :title=''
            OR LOWER(p.title) LIKE LOWER(CONCAT('%', :title, '%')))
            AND p.user.email=:email
            ORDER BY p.title ASC
            """)
    List<Priority> findByTitle(@Param("title") String title, @Param("email") String email);
}
