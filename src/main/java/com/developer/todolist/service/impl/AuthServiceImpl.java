package todolist.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import todolist.entity.Role;
import todolist.entity.User;
import todolist.model.AuthResponse;
import todolist.model.LoginRequest;
import todolist.model.RegisterRequest;
import todolist.repository.UserRepo;
import todolist.security.CustomUserDetailsService;
import todolist.security.JwtService;
import todolist.service.AuthService;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepo userRepo;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    private final CustomUserDetailsService userDetailsService;

    private final JwtService jwtService;

    @Override
    public AuthResponse register(RegisterRequest request) {

        if (userRepo.existsByUsername(request.getUsername())) {

            throw new RuntimeException("Username already exists");
        }

        User user = new User();

        user.setUsername(request.getUsername());

        user.setPassword(passwordEncoder.encode(request.getPassword()));

        user.setRole(Role.USER);

        User savedUser = userRepo.save(user);

        UserDetails userDetails = userDetailsService.loadUserByUsername(
                        savedUser.getUsername());

        String token = jwtService.generateToken(userDetails);

        return new AuthResponse(token,
                savedUser.getUsername(), savedUser.getRole().name());
    }

    @Override
    public AuthResponse login(LoginRequest request) {

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                        request.getUsername(), request.getPassword()));

        User user = userRepo.findByUsername(
                                request.getUsername()).orElseThrow(() ->
                                new RuntimeException("User not found"));

        UserDetails userDetails = userDetailsService.loadUserByUsername(
                        user.getUsername());

        String token = jwtService.generateToken(userDetails);

        return new AuthResponse(token, user.getUsername(),
                user.getRole().name());
    }
}
