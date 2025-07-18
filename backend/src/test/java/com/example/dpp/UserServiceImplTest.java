package com.example.dpp;

import com.example.dpp.model.Role;
import com.example.dpp.model.api.auth.RegisterUser;
import com.example.dpp.model.api.auth.RoleAssignment;
import com.example.dpp.model.api.auth.UserInfo;
import com.example.dpp.model.db.auth.User;
import com.example.dpp.repository.UserRepository;
import com.example.dpp.services.UserServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {

    }

    @Test
    void register_success() {
        var registerUser = new RegisterUser();
        registerUser.setUserName("user");
        registerUser.setEmail("user@example.com");
        registerUser.setPassword("password");

        when(userRepository.findByEmail("user@example.com")).thenReturn(Optional.empty());
        when(userRepository.findByUserName("user")).thenReturn(Optional.empty());

        var result = userService.register(registerUser);

        assertThat(result).isTrue();
        verify(userRepository).save(any());
    }

    @Test
    void register_userExists_returnsFalse() {
        var registerUser = new RegisterUser();
        registerUser.setUserName("user");
        registerUser.setEmail("user@example.com");
        registerUser.setPassword("password");
        when(userRepository.findByEmail("user@example.com")).thenReturn(Optional.of(mock(User.class)));

        assertThat(userService.register(registerUser)).isFalse();
    }

    @Test
    void getUserInfo_found() {
        var user = mock(User.class);
        when(user.getUserData()).thenReturn(new UserInfo());
        when(userRepository.findById(1)).thenReturn(Optional.of(user));

        var result = userService.getUserInfo(1);

        assertThat(result).isNotNull();
    }

    @Test
    void getUserInfo_notFound_throws() {
        when(userRepository.findById(1)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> userService.getUserInfo(1)).isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    void updateUser_savesUpdatedUser() {
        var info = new UserInfo();
        info.setEmail("new@email.com");
        info.setUserName("newUser");
        info.setFirstName("newFirst");
        info.setLastName("newLast");
        info.setId(1);

        var user = new User();
        when(userRepository.findById(1)).thenReturn(Optional.of(user));

        userService.updateUser(info);

        assertThat(user.getFirstName()).isEqualTo("newFirst");
        assertThat(user.getLastName()).isEqualTo("newLast");
        assertThat(user.getEmail()).isEqualTo("new@email.com");
        assertThat(user.getUserName()).isEqualTo("newUser");
        verify(userRepository).save(user);
    }

    @Test
    void deleteUser_existingUser_deletes() {
        when(userRepository.existsById(1)).thenReturn(true);

        userService.deleteUser(1);

        verify(userRepository).deleteById(1);
    }

    @Test
    void deleteUser_nonExistingUser_doesNothing() {
        when(userRepository.existsById(1)).thenReturn(false);

        userService.deleteUser(1);

        verify(userRepository, never()).deleteById(anyInt());
    }

    @Test
    void userExistsByEmail_returnsCorrectly() {
        when(userRepository.findByEmail("mail@test.com")).thenReturn(Optional.of(new User()));
        assertThat(userService.userExistsByEmail("mail@test.com")).isTrue();
    }

    @Test
    void userExistsByUserName_returnsCorrectly() {
        when(userRepository.findByUserName("testuser")).thenReturn(Optional.of(new User()));
        assertThat(userService.userExistsByUserName("testuser")).isTrue();
    }

    @Test
    void userExistsById_returnsCorrectly() {
        when(userRepository.findById(1)).thenReturn(Optional.of(new User()));
        assertThat(userService.userExistsById(1)).isTrue();
    }

    @Test
    void setRole_setsUserRole() {
        var user = new User();
        when(userRepository.findById(1)).thenReturn(Optional.of(user));

        var role = new RoleAssignment();
        role.setId(1);
        role.setRole(Role.ADMINISTRATOR);

        var result = userService.setRole(role);

        assertThat(result).isTrue();
        assertThat(user.getRole()).isEqualTo(Role.ADMINISTRATOR);
    }

    @Test
    void getUsers_returnsUserInfos() {
        var user = mock(User.class);
        when(user.getUserData()).thenReturn(new UserInfo());
        when(userRepository.findAll()).thenReturn(List.of(user));

        var result = userService.getUsers();

        assertThat(result).hasSize(1);
    }

    @Test
    void unlockUser_setsAccountLockedFalse() {
        var user = new User();
        user.setAccountLocked(true);
        when(userRepository.findById(1)).thenReturn(Optional.of(user));

        userService.unlockUser(1);

        assertThat(user.isAccountLocked()).isFalse();
    }

    @Test
    void verifyTotp_disabledOrNoSecret_returnsFalse() {
        var user = new User();
        user.setMfaEnabled(false);
        user.setMfaSecret(null);
        when(userRepository.findById(1)).thenReturn(Optional.of(user));

        assertThat(userService.verifyTotp(1, "000000")).isFalse();
    }

    @Test
    void updateUser_userNotFound_throws() {
        when(userRepository.findById(1)).thenReturn(Optional.empty());

        var userInfo = new UserInfo();
        userInfo.setId(1);

        assertThatThrownBy(() -> userService.updateUser(userInfo))
                .isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    void addFailedLogin_userNotFound_throws() {
        when(userRepository.findById(1)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> userService.addFailedLogin(1))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("User not found");
    }

    @Test
    void unlockUser_userNotFound_throws() {
        when(userRepository.findById(1)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> userService.unlockUser(1))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("User not found");
    }

    @Test
    void verifyTotp_userNotFound_throws() {
        when(userRepository.findById(1)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> userService.verifyTotp(1, "123456"))
                .isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    void setRole_userNotFound_throws() {
        when(userRepository.findById(1)).thenReturn(Optional.empty());

        var roleAssignment = new RoleAssignment();
        roleAssignment.setId(1);
        roleAssignment.setRole(Role.ADMINISTRATOR);

        assertThatThrownBy(() -> userService.setRole(roleAssignment))
                .isInstanceOf(EntityNotFoundException.class);
    }
}

