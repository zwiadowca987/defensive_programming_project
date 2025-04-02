package com.example.dpp.repository;

import com.example.dpp.model.Role;
import com.example.dpp.model.User;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class UserRepository {
    private final List<User> userList = new ArrayList<>();

    public UserRepository() {
        // Constructor goes here (if required)
    }

    public List<User> findAll() {
        return userList;
    }

    public Optional<User> findById(Integer id) {
        return userList.stream().filter(user -> user.id().equals(id)).findFirst();
    }

    public void save(User user) {
        userList.removeIf(u -> u.id().equals(user.id()));
        userList.add(user);
    }

    public boolean existById(Integer id) {
        return userList.stream().filter(u -> u.id().equals(id)).count() == 1;
    }

    public void delete(Integer id) {
        userList.removeIf(u -> u.id().equals(id));
    }

    @PostConstruct
    private void init() {
        userList.add(new User(0, "Placeholder UserName", "Placeholder FirstName", "Placeholder LastName", "Placeholder Password", Role.ADMINISTRATOR));
    }
}
