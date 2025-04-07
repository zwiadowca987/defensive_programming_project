package com.example.dpp.model;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

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

    public User() {
    }

    public User(String firstName, Integer id, String userName, String lastName, String email, String password, Role role) {
        this.firstName = firstName;
        this.id = id;
        this.userName = userName;
        this.lastName = lastName;
        this.email = email;
        this.password = new PasswordHash(password);
        this.role = role;
    }

    public User(UserCRUD user){
        this.firstName = user.getFirstName();
        this.userName = user.getUserName();
        this.lastName = user.getLastName();
        this.email = user.getEmail();
        this.password = new PasswordHash(user.getPassword());
        this.role = Role.USER;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public UserCRUD getUserData() {
        var userData = new UserCRUD();
        userData.setFirstName(firstName);
        userData.setLastName(lastName);
        userData.setEmail(email);
        userData.setUserName(userName);
        userData.setRole(role);
        return userData;
    }
}
