package todolist.service;

import org.springframework.data.domain.Page;
import todolist.model.TodoRequest;
import todolist.model.TodoResponse;

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
