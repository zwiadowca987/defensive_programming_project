package com.example.dpp.services;

import com.example.dpp.model.api.auth.*;
import com.example.dpp.model.db.auth.User;
import com.example.dpp.repository.UserRepository;
import com.example.dpp.security.TotpUtil;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
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
        return (user.map(User::getUserData)
                .orElseThrow(() -> new EntityNotFoundException("User with id " + id + " not found")));
    }

    @Override
    public UserInfo getUserInfoByUsername(String username) {
        var user = repository.findByUserName(username);
        return (user.map(User::getUserData)
                .orElseThrow(() -> new EntityNotFoundException("User " + username + " not found")));
    }

    @Override
    public UserInfo getUserInfoByEmail(String email) {
        var user = repository.findByEmail(email);
        return (user.map(User::getUserData)
                .orElseThrow(() -> new EntityNotFoundException("User " + email + " not found")));
    }

    @Override
    public User getUserByUsername(String username) {
        return repository.findByUserName(username)
                .orElseThrow(() -> new EntityNotFoundException("User " + username + " not found"));
    }

    @Override
    public List<UserInfo> getUsers() {
        return repository.findAll().stream().map(User::getUserData).collect(Collectors.toList());
    }

    @Override
    public void updateUser(UserInfo userInfo) {
        var oldUser = repository.findById(userInfo.getId()).orElseThrow(() -> new EntityNotFoundException("User with id " + userInfo.getId() + " not found"));
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
        var user = repository.findById(roleAssignment.getId())
                .orElseThrow(() -> new EntityNotFoundException("User with id " + roleAssignment.getId() + " not found"));
        user.setRole(roleAssignment.getRole());
        return true;
    }

    @Override
    public void addFailedLogin(int id) {
        var user = repository.findById(id).orElseThrow(() -> new IllegalArgumentException("User not found."));
        user.setFailedLoginAttempts(user.getFailedLoginAttempts() + 1);
        if (user.getFailedLoginAttempts() >= 5) {
            user.setLockTime(LocalDateTime.now());
            user.setAccountLocked(true);
        }
        repository.save(user);
    }

    @Override
    public void unlockUser(int id) {
        var user = repository.findById(id).orElseThrow(() -> new IllegalArgumentException("User not found."));
        user.setAccountLocked(false);
    }

    @Override
    public boolean isUserLocked(int id) {
        var user = repository.findById(id).orElseThrow(() -> new IllegalArgumentException("User not found."));
        if (user.isAccountLocked() && user.getLockTime().plusMinutes(15).isBefore(LocalDateTime.now())) {
            user.setAccountLocked(false);
            user.setFailedLoginAttempts(0);
            repository.save(user);
        } else if (user.getFailedLoginAttempts() >= 5 && !user.isAccountLocked()) {
            user.setLockTime(LocalDateTime.now());
            user.setAccountLocked(true);
            repository.save(user);
        }
        return user.isAccountLocked();
    }

    @Override
    public void resetFailedLoginAttempts(int id) {
        var user = repository.findById(id).orElseThrow(() -> new IllegalArgumentException("User not found."));
        user.setFailedLoginAttempts(0);
        user.setAccountLocked(false);
    }

    @Override
    public String generateMfaSecret(int userId) {

        var user = repository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User with id " + userId + " not found"));

        var secret = TotpUtil.generateSecret();

        user.setMfaSecret(secret);
        user.setMfaEnabled(false);
        user.setMfaVerified(false);
        repository.save(user);
        return secret;
    }

    @Override
    public boolean verifyAndEnableMfa(int id, String totpCode) {
        User user = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User with id " + id + " not found"));
        if (user.isMfaEnabled() || user.getMfaSecret() == null) {
            return false;
        }

        var verified = TotpUtil.verifyCode(user.getMfaSecret(), totpCode);

        if (verified) {
            user.setMfaVerified(true);
            user.setMfaEnabled(true);
        }
        return verified;
    }

    @Override
    public boolean disableMfa(int id) {
        User user = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User with id " + id + " not found"));

        user.setMfaEnabled(false);
        user.setMfaSecret(null);
        user.setMfaVerified(false);

        return true;
    }

    @Override
    public boolean verifyTotp(int id, String totpCode) {
        User user = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User with id " + id + " not found"));
        if (!user.isMfaEnabled() || user.getMfaSecret() == null) {
            return false;
        }

        return TotpUtil.verifyCode(user.getMfaSecret(), totpCode);
    }
}
