package todolist.service;

import todolist.model.AuthResponse;
import todolist.model.LoginRequest;
import todolist.model.RegisterRequest;

public interface AuthService {

    AuthResponse register(RegisterRequest request);

    AuthResponse login(LoginRequest request);
}