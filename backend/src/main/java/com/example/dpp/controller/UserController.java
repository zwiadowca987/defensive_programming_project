package com.example.dpp.controller;

import com.example.dpp.model.User;
import com.example.dpp.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@CrossOrigin
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
        if (!repository.existById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User Not Found");
        }

        repository.save(user);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        repository.delete(id);
    }
}
