package todolist.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import todolist.entity.Todos;
import todolist.entity.User;
import todolist.model.TodoRequest;
import todolist.model.TodoResponse;
import todolist.repository.TodoRepo;
import todolist.repository.UserRepo;
import todolist.service.TodoService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TodoServiceImpl implements TodoService {

    private final TodoRepo todoRepo;
    private final UserRepo userRepo;

    @Override
    public TodoResponse createTodo(TodoRequest todoRequest, String username) {

        User user = userRepo.findByUsername(username).orElseThrow(() ->
                        new RuntimeException("User not found"));

        Todos todo = new Todos();
        todo.setTitle(todoRequest.getTitle());
        todo.setCompleted(todoRequest.isCompleted());

        todo.setUser(user);
        Todos savedTodo = todoRepo.save(todo);

        // Map Saved Entity -> Response DTO
        return mapToResponse(savedTodo);
    }

    @Override
    public List<TodoResponse> getTodos(String username) {

        // Find logged-in user
        User user = userRepo.findByUsername(username).orElseThrow(() ->
                        new RuntimeException("User not found"));

        // Get only this user's Todos
        return todoRepo.findByUser(user).stream().
                map(this::mapToResponse).collect(Collectors.toList());
    }

    private TodoResponse mapToResponse(Todos todo) {
        TodoResponse response = new TodoResponse();
        response.setId(todo.getId());
        response.setTitle(todo.getTitle());
        response.setCompleted(todo.isCompleted());
        response.setCreatedAt(todo.getCreatedAt());
        response.setUpdatedAt(todo.getUpdatedAt());
        return response;
    }

}
