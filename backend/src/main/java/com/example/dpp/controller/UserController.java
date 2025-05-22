package com.example.dpp.controller;

import com.example.dpp.model.api.auth.LoginCredentials;
import com.example.dpp.model.api.auth.RegisterUser;
import com.example.dpp.model.api.auth.RoleAssignment;
import com.example.dpp.model.api.auth.UserInfo;
import com.example.dpp.services.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {
    private final IUserService service;

    @Autowired
    public UserController(IUserService service) {
        this.service = service;
    }

    @GetMapping("")
    public List<UserInfo> findAll() {
        return service.getUsers();
    }

    @GetMapping("/{id}")
    public UserInfo findById(@PathVariable Integer id) {
        var user = service.getUserInfo(id);
        if (user == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User Not Found");
        }
        return user;
    }


//    @ResponseStatus(HttpStatus.CREATED)
//    @PostMapping("")
//    public void create(@RequestBody RegisterUser user) {
//        repository.save(new User(user));
//    }

    @PutMapping("/{id}")
    public void update(@RequestBody UserInfo user, @PathVariable Integer id) {
        if (service.getUserInfo(id) == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User Not Found");
        }
        service.updateUser(user);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        service.deleteUser(id);
    }

    @PostMapping("/login")
    public UserInfo login(@RequestBody LoginCredentials loginData) {
        return service.login(loginData);
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegisterUser user) {

        if (service.userExistsByEmail(user.getEmail())) {
            return ResponseEntity.badRequest().body("Email już istnieje");
        }

        if (service.userExistsByUserName(user.getUserName())) {
            return ResponseEntity.badRequest().body("Nazwa użytkownika już istnieje");
        }

        service.register(user);
        return ResponseEntity.ok("Zarejestrowano pomyślnie");
    }

    @PostMapping("/role")
    public ResponseEntity<?> addRole(@RequestBody RoleAssignment role) {
        if (!service.userExistsById(role.getId())) {
            return ResponseEntity.badRequest().body("Użytkownik nie istnieje");
        }
        service.setRole(role);
        return ResponseEntity.ok("Rola ustawiona");
    }
}
