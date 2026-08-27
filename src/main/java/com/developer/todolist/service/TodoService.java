package com.developer.todolist.service;

import com.developer.todolist.model.TodoRequest;
import com.developer.todolist.model.TodoResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface TodoService {

    TodoResponse createTodo(
            TodoRequest request,
            String username
    );

    Page<TodoResponse> getTodos(
            String username,
            int page,
            int size,
            Boolean completed,
            String search
    );

    TodoResponse getTodoById(
            Long id,
            String username
    );

    TodoResponse updateTodo(
            Long id,
            TodoRequest request,
            String username
    );

    void deleteTodo(
            Long id,
            String username
    );
}
