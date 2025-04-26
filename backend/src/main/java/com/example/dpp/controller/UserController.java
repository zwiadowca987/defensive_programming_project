package com.example.dpp.controller;

import com.example.dpp.model.auth.LoginCredentials;
import com.example.dpp.model.auth.User;
import com.example.dpp.model.auth.UserCRUD;
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
    public UserCRUD findById(@PathVariable Integer id) {
        return repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User Not Found")).getUserData();
    }


    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("")
    public void create(@RequestBody UserCRUD user) {
        repository.save(new User(user));
    }
    
    @PutMapping("/{id}")
    public void update(@RequestBody UserCRUD user, @PathVariable Integer id) {
        if (!repository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User Not Found");
        }

        repository.deleteById(id);
        repository.save(new User(user));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        repository.deleteById(id);
    }

    @PostMapping("/login")
    public UserCRUD login(@RequestBody LoginCredentials loginData) {
        return repository.findByEmail(loginData.getEmail())
                .filter(user -> user.getPassword().checkPassword(loginData.getPassword()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid credentials"))
                .getUserData();
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody UserCRUD user) {
        if (repository.findByEmail(user.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body("Email już istnieje");
        }

        if (repository.findByUserName(user.getUserName()).isPresent()) {
            return ResponseEntity.badRequest().body("Nazwa użytkownika już istnieje");
        }

        var newUser = new User(user);
        repository.save(newUser);
        return ResponseEntity.ok("Zarejestrowano pomyślnie");
    }
}
