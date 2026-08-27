package com.developer.todolist.service;

import com.developer.todolist.model.AuthResponse;
import com.developer.todolist.model.LoginRequest;
import com.developer.todolist.model.RegisterRequest;

public interface AuthService {

    AuthResponse register(RegisterRequest request);

    AuthResponse login(LoginRequest request);
}