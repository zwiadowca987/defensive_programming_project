package com.example.dpp.model.api.auth;

import com.example.dpp.model.Role;

public class RoleAssignment {
    private Integer id;
    private Role role;

    public RoleAssignment() {
    }

    public RoleAssignment(Integer id, Role role) {
        this.id = id;
        this.role = role;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
