package todolist.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import todolist.model.TodoRequest;
import todolist.model.TodoResponse;
import todolist.service.TodoService;

import java.util.List;

@RestController
@RequestMapping("/todo-list")
@RequiredArgsConstructor
public class TodoController {

    @Autowired
    private final TodoService todoService;

    @PostMapping
    public ResponseEntity<TodoResponse> createTodo(@Valid @RequestBody TodoRequest todoRequest,
                                                   Authentication authentication){

        String username = authentication.getName();
        TodoResponse response = todoService.createTodo(todoRequest, username);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<TodoResponse>> getTodos(Authentication authentication) {

        String username = authentication.getName();
        List<TodoResponse> responses = todoService.getTodos(username);
        return ResponseEntity.ok(responses);
    }
}
