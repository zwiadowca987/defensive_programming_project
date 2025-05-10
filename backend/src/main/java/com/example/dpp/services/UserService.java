package com.example.dpp.services;

import com.example.dpp.model.api.auth.*;
import com.example.dpp.model.db.auth.User;
import com.example.dpp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService implements IUserService {

    private final UserRepository repository;

    @Autowired
    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public UserInfo login(LoginCredentials credentials) {
        return repository.findByEmail(credentials.getEmail())
                .filter(user -> user.getPassword().checkPassword(credentials.getPassword()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid credentials"))
                .getUserData();
    }

    @Override
    public Boolean register(RegisterUser user) {

        if (repository.findByEmail(user.getEmail()).isPresent()
                || repository.findByUserName(user.getUserName()).isPresent()) {
            return false;
        }

        var newUser = new User(user);
        repository.save(newUser);
        return true;
    }

    @Override
    public UserInfo getUserInfo(int id) {
        var user = repository.findById(id);
        return (user.map(User::getUserData).orElse(null));
    }

    @Override
    public List<UserInfo> getUsers() {
        return repository.findAll().stream().map(User::getUserData).collect(Collectors.toList());
    }

    @Override
    public void updateUser(UserInfo userInfo) {
        var oldUser = repository.findById(userInfo.getId()).orElseThrow();
        oldUser.setFirstName(userInfo.getFirstName());
        oldUser.setLastName(userInfo.getLastName());
        oldUser.setEmail(userInfo.getEmail());
        oldUser.setUserName(userInfo.getUserName());
        repository.save(oldUser);
    }

    @Override
    public void deleteUser(int id) {
        if (!repository.existsById(id))
            return;
        repository.deleteById(id);
    }

    @Override
    public boolean changePassword(PasswordChange passwordChange) {
        return false;
    }

    @Override
    public boolean userExistsByEmail(String email) {
        return repository.findByEmail(email).isPresent();
    }

    @Override
    public boolean userExistsByUserName(String userName) {
        return repository.findByUserName(userName).isPresent();
    }

    @Override
    public boolean userExistsById(int id) {
        return repository.findById(id).isPresent();
    }

    @Override
    public boolean setRole(RoleAssignment roleAssignment) {
        var user = repository.findById(roleAssignment.getId()).orElseThrow();
        user.setRole(roleAssignment.getRole());
        return true;
    }
}
