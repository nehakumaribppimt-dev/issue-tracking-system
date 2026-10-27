package com.neha.issuetracker.controller;

import com.neha.issuetracker.model.User;
import com.neha.issuetracker.repository.UserRepository;
import com.neha.issuetracker.security.JwtService;
import org.springframework.http.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final UserRepository users;
    private final PasswordEncoder passwords;
    private final JwtService jwt;

    public AuthController(UserRepository users, PasswordEncoder passwords, JwtService jwt) {
        this.users = users;
        this.passwords = passwords;
        this.jwt = jwt;
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public Map<String, String> register(@RequestBody Register data) {
        if (data.email() == null || data.password() == null || data.password().length() < 8 || data.name() == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Name, email and password (8+ characters) required");
        if (users.findByEmail(data.email()).isPresent())
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email already registered");
        User user = new User();
        user.setName(data.name());
        user.setEmail(data.email());
        user.setPassword(passwords.encode(data.password()));
        user.setRole("USER");
        users.save(user);
        return Map.of("message", "Account created");
    }

    @PostMapping("/login")
    public Map<String, String> login(@RequestBody Login data) {
        User user = users.findByEmail(data.email()).orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid credentials"));
        if (!passwords.matches(data.password(), user.getPassword()))
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid credentials");
        return Map.of("token", jwt.create(user.getEmail()));
    }

    record Login(String email, String password) {
    }

    record Register(String name, String email, String password) {
    }
}
