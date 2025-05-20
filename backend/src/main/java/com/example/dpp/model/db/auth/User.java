package com.example.dpp.model.db.auth;

import com.example.dpp.model.Role;
import com.example.dpp.model.api.auth.RegisterUser;
import com.example.dpp.model.api.auth.UserInfo;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "user_name", nullable = false, unique = true)
    private String userName;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "password_id", referencedColumnName = "id")
    private PasswordHash password;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(name = "failed_login_attempts", columnDefinition = "integer default 0")
    private int failedLoginAttempts;

    @Column(name = "account_locked", columnDefinition = "boolean default false")
    private boolean accountLocked;

    @Column(name = "lock_time")
    private LocalDateTime lockTime;

    @Column
    private boolean mfaEnabled = false;

    @Column
    private String mfaSecret;

    @Column
    private boolean mfaVerified = false;


    public User() {
    }

    public User(int id, String userName, String firstName, String lastName, String email,
                PasswordHash password, Role role, int failedLoginAttempts,
                boolean accountLocked, LocalDateTime lockTime) {
        this.id = id;
        this.userName = userName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.role = role;
        this.failedLoginAttempts = failedLoginAttempts;
        this.accountLocked = accountLocked;
        this.lockTime = lockTime;
    }

    public User(RegisterUser user) {
        this.firstName = user.getFirstName();
        this.userName = user.getUserName();
        this.lastName = user.getLastName();
        this.email = user.getEmail();
        this.password = new PasswordHash(user.getPassword());
        this.role = Role.USER;
        this.failedLoginAttempts = 0;
        this.accountLocked = false;
        this.lockTime = LocalDateTime.now().minusDays(1);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String mail) {
        this.email = mail;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public PasswordHash getPassword() {
        return password;
    }

    public void setPassword(PasswordHash password) {
        this.password = password;
    }

    public void setPassword(String password) {
        this.password = new PasswordHash(password);
    }

    public int getFailedLoginAttempts() {
        return failedLoginAttempts;
    }

    public void setFailedLoginAttempts(int failedLoginAttempts) {
        this.failedLoginAttempts = failedLoginAttempts;
    }

    public LocalDateTime getLockTime() {
        return lockTime;
    }

    public void setLockTime(LocalDateTime lockTime) {
        this.lockTime = lockTime;
    }

    public boolean isAccountLocked() {
        return accountLocked;
    }

    public void setAccountLocked(boolean accountLocked) {
        this.accountLocked = accountLocked;
    }

    public String getMfaSecret() {
        return mfaSecret;
    }

    public void setMfaSecret(String mfaSecret) {
        this.mfaSecret = mfaSecret;
    }

    public boolean isMfaVerified() {
        return mfaVerified;
    }

    public void setMfaVerified(boolean mfaVerified) {
        this.mfaVerified = mfaVerified;
    }

    public boolean isMfaEnabled() {
        return mfaEnabled;
    }

    public void setMfaEnabled(boolean mfaEnabled) {
        this.mfaEnabled = mfaEnabled;
    }

    public UserInfo getUserData() {
        var userData = new UserInfo();
        userData.setId(id);
        userData.setFirstName(firstName);
        userData.setLastName(lastName);
        userData.setEmail(email);
        userData.setUserName(userName);
        userData.setRole(role);
        return userData;
    }
}
