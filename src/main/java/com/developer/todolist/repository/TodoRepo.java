package com.developer.todolist.repository;

import com.developer.todolist.entity.Todos;
import com.developer.todolist.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.Optional;

@Repository
public interface TodoRepo extends JpaRepository<Todos, Long> {

    Page<Todos> findByUser(
            User user,
            Pageable pageable
    );

    Page<Todos> findByUserAndCompleted(
            User user,
            boolean completed,
            Pageable pageable
    );

    Page<Todos> findByUserAndTitleContainingIgnoreCase(
            User user,
            String title,
            Pageable pageable
    );

    Page<Todos> findByUserAndCompletedAndTitleContainingIgnoreCase(
            User user,
            boolean completed,
            String title,
            Pageable pageable
    );

    Optional<Todos> findByIdAndUser(
            Long id,
            User user
    );
}