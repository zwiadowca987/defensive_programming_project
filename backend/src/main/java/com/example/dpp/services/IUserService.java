package com.example.dpp.services;

import com.example.dpp.model.api.auth.*;
import com.example.dpp.model.auth.Role;

import java.util.List;

public interface IUserService {
    UserInfo login(LoginCredentials credentials);
    Boolean register(RegisterUser user);
    UserInfo getUserInfo(int id);
    List<UserInfo> getUsers();
    void updateUser(UserInfo userInfo);
    void deleteUser(int id);
    boolean changePassword(PasswordChange passwordChange);
    boolean userExistsByEmail(String email);
    boolean userExistsByUserName(String userName);
    boolean userExistsById(int id);
    boolean setRole(RoleAssignment roleAssignment);
}
