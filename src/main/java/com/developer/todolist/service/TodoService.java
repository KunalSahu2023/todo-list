package todolist.service;

import todolist.model.TodoRequest;
import todolist.model.TodoResponse;

import java.util.List;

public interface TodoService {
    TodoResponse createTodo(TodoRequest todos, String username);

    List<TodoResponse> getTodos(String username);
}
