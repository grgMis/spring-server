package com.example.monitoring.model;

import jakarta.persistence.*;

@Entity
@Table(name = "user_role")
public class UserRole {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer user_role_id;

    @Column(name = "user_role_name", nullable = false, length = 40)
    private String user_role_name;

    public UserRole() {
    }

    public UserRole(String user_role_name) {
        this.user_role_name = user_role_name;
    }

    public Integer getUser_role_id() {
        return user_role_id;
    }

    public void setUser_role_id(Integer user_role_id) {
        this.user_role_id = user_role_id;
    }

    public String getUser_role_name() {
        return user_role_name;
    }

    public void setUser_role_name(String user_role_name) {
        this.user_role_name = user_role_name;
    }
}
