package com.example.dpp.services;

import com.example.dpp.model.api.auth.*;
import com.example.dpp.model.db.auth.User;

import java.util.List;

public interface IUserService {
    UserInfo login(LoginCredentials credentials);

    Boolean register(RegisterUser user);

    UserInfo getUserInfo(int id);

    UserInfo getUserInfoByUsername(String username);

    UserInfo getUserInfoByEmail(String email);

    User getUserByUsername(String username);

    List<UserInfo> getUsers();

    void updateUser(UserInfo userInfo);

    void deleteUser(int id);

    boolean changePassword(PasswordChange passwordChange);

    boolean userExistsByEmail(String email);

    boolean userExistsByUserName(String userName);

    boolean userExistsById(int id);

    boolean setRole(RoleAssignment roleAssignment);

    void addFailedLogin(int id);

    void unlockUser(int id);

    boolean isUserLocked(int id);

    void resetFailedLoginAttempts(int id);

    String generateMfaSecret(int id);

    boolean verifyAndEnableMfa(int id, String totpCode);

    boolean disableMfa(int id);

    boolean verifyTotp(int id, String totpCode);
}
