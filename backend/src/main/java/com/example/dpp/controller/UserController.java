package com.example.dpp.controller;

import com.example.dpp.model.Role;
import com.example.dpp.model.User;
import com.example.dpp.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {
    private final UserRepository repository;

    public UserController(UserRepository repository) {
        this.repository = repository;
    }

    @GetMapping("")
    public List<User> findAll() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public User findById(@PathVariable Integer id) {
        return repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User Not Found"));
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("")
    public void create(@RequestBody User user) {
        repository.save(user);
    }

    @PutMapping("/{id}")
    public void update(@RequestBody User user, @PathVariable Integer id) {
        if (!repository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User Not Found");
        }

        repository.deleteById(id);
        repository.save(user);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        repository.deleteById(id);
    }

    @PostMapping("/login")
    public User login(@RequestBody User loginData) {
        return repository.findByEmail(loginData.getEmail())
                .filter(user -> user.getPassword().equals(loginData.getPassword()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid credentials"));
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        if (repository.findByEmail(user.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body("Email już istnieje");
        }

        if (repository.findByUserName(user.getUserName()).isPresent()) {
            return ResponseEntity.badRequest().body("Nazwa użytkownika już istnieje");
        }

        user.setPassword(user.getPassword());
        user.setRole(Role.USER);
        repository.save(user);
        return ResponseEntity.ok("Zarejestrowano pomyślnie");
    }
}
