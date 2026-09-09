package com.developer.todolist.controller;

import com.developer.todolist.model.AuthResponse;
import com.developer.todolist.model.LoginRequest;
import com.developer.todolist.model.RegisterRequest;
import com.developer.todolist.service.AuthService;
import com.developer.todolist.service.JwtBlacklistService;
import com.developer.todolist.service.RateLimitService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    private final JwtBlacklistService jwtBlacklistService;

    private final RateLimitService rateLimitService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(
            @Valid @RequestBody RegisterRequest request) {

        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(
            @Valid @RequestBody LoginRequest request,
            HttpServletRequest httpServletRequest
    ) {

        String clientIp = httpServletRequest.getRemoteAddr();

        String rateLimitKey = "rate_limit:login:" + clientIp;

        boolean allowed = rateLimitService.isAllowed(rateLimitKey);

        if (!allowed) {
            return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS)
                    .body("Too many login attempts. Please try again later.");
        }

        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(
            @RequestHeader("Authorization") String authorizationHeader
    ) {

        if (authorizationHeader == null ||
                !authorizationHeader.startsWith("Bearer ")) {

            return ResponseEntity.badRequest()
                    .body("Invalid Authorization header");
        }

        String token = authorizationHeader.substring(7);

        jwtBlacklistService.logout(token);

        return ResponseEntity.ok("Logout successful");
    }
}