package org.example.backend.todo.repository;

import org.example.backend.todo.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    List<Category> findByUserEmailOrderByTitleAsc(String email);

    @Query("""
            SELECT c FROM Category c
            WHERE (:title is null or :title=''
            OR LOWER(c.title) LIKE LOWER(CONCAT('%', :title, '%') ))
            AND c.user.email=:email
            ORDER BY c.title ASC
            """)
    List<Category> findByTitle(@Param("title") String title, @Param("email") String email);
}
