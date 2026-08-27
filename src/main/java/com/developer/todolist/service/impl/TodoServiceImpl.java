package com.developer.todolist.service.impl;

import com.developer.todolist.entity.Todos;
import com.developer.todolist.entity.User;
import com.developer.todolist.exception.ResourceNotFoundException;
import com.developer.todolist.model.TodoRequest;
import com.developer.todolist.model.TodoResponse;
import com.developer.todolist.repository.TodoRepo;
import com.developer.todolist.repository.UserRepo;
import com.developer.todolist.service.TodoService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
@Transactional
public class TodoServiceImpl implements TodoService {

    private final TodoRepo todoRepo;
    private final UserRepo userRepo;

    @Override
    public TodoResponse createTodo(
            TodoRequest request,
            String username
    ) {

        User user = getUser(username);

        Todos todo = Todos.builder()
                .title(request.getTitle())
                .completed(request.isCompleted())
                .user(user)
                .build();

        Todos savedTodo = todoRepo.save(todo);

        return mapToResponse(savedTodo);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TodoResponse> getTodos(
            String username,
            int page,
            int size,
            Boolean completed,
            String search
    ) {

        User user = getUser(username);

        Pageable pageable =
                PageRequest.of(page, size);

        Page<Todos> todos;

        if (completed != null) {

            todos = todoRepo.findByUserAndCompleted(
                    user,
                    completed,
                    pageable
            );

        } else if (search != null &&
                !search.trim().isEmpty()) {

            todos = todoRepo
                    .findByUserAndTitleContainingIgnoreCase(
                            user,
                            search,
                            pageable
                    );

        } else {

            todos = todoRepo.findByUser(
                    user,
                    pageable
            );
        }

        return todos.map(this::mapToResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public TodoResponse getTodoById(
            Long id,
            String username
    ) {

        User user = getUser(username);

        Todos todo = todoRepo
                .findByIdAndUser(id, user)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Todo not found with id: " + id
                        )
                );

        return mapToResponse(todo);
    }

    @Override
    public TodoResponse updateTodo(
            Long id,
            TodoRequest request,
            String username
    ) {

        User user = getUser(username);

        Todos todo = todoRepo
                .findByIdAndUser(id, user)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Todo not found with id: " + id
                        )
                );

        todo.setTitle(request.getTitle());
        todo.setCompleted(request.isCompleted());

        Todos updatedTodo =
                todoRepo.save(todo);

        return mapToResponse(updatedTodo);
    }

    @Override
    public void deleteTodo(
            Long id,
            String username
    ) {

        User user = getUser(username);

        Todos todo = todoRepo
                .findByIdAndUser(id, user)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Todo not found with id: " + id
                        )
                );

        todoRepo.delete(todo);
    }

    private User getUser(String username) {

        return userRepo
                .findByUsername(username)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User not found: " + username
                        )
                );
    }

    private TodoResponse mapToResponse(
            Todos todo
    ) {

        TodoResponse response =
                new TodoResponse();

        response.setId(todo.getId());
        response.setTitle(todo.getTitle());
        response.setCompleted(todo.isCompleted());
        response.setCreatedAt(todo.getCreatedAt());
        response.setUpdatedAt(todo.getUpdatedAt());

        return response;
    }
}
