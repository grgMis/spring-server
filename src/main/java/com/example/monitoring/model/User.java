package com.example.monitoring.model;

import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer user_id;

    @Column(name = "user_last_name", nullable = false, length = 40)
    private String user_last_name;

    @Column(name = "user_first_name", nullable = false, length = 40)
    private String user_first_name;

    @Column(name = "user_father_name", nullable = false, length = 40)
    private String user_father_name;

    @Column(name = "user_login", nullable = false, length = 40)
    private String user_login;

    @Column(name = "user_password", nullable = false, length = 40)
    private String user_password;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "user_role_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private UserRole userRole;

    public User() {
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public String getUser_last_name() {
        return user_last_name;
    }

    public void setUser_last_name(String user_last_name) {
        this.user_last_name = user_last_name;
    }

    public String getUser_first_name() {
        return user_first_name;
    }

    public void setUser_first_name(String user_first_name) {
        this.user_first_name = user_first_name;
    }

    public String getUser_father_name() {
        return user_father_name;
    }

    public void setUser_father_name(String user_father_name) {
        this.user_father_name = user_father_name;
    }

    public String getUser_login() {
        return user_login;
    }

    public void setUser_login(String user_login) {
        this.user_login = user_login;
    }

    public String getUser_password() {
        return user_password;
    }

    public void setUser_password(String user_password) {
        this.user_password = user_password;
    }

    public UserRole getUserRole() {
        return userRole;
    }

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }
}
