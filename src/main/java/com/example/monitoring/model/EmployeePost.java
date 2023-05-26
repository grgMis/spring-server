package com.example.monitoring.model;

import jakarta.persistence.*;

@Entity
@Table(name = "employee_post")
public class EmployeePost {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer employee_post_id;

    @Column(name = "employee_post_name", nullable = false, length = 40)
    private String employee_post_name;

    public EmployeePost() {
    }

    public EmployeePost(String employee_post_name) {
        this.employee_post_name = employee_post_name;
    }

    public Integer getEmployee_post_id() {
        return employee_post_id;
    }

    public void setEmployee_post_id(Integer employee_post_id) {
        this.employee_post_id = employee_post_id;
    }

    public String getEmployee_post_name() {
        return employee_post_name;
    }

    public void setEmployee_post_name(String employee_post_name) {
        this.employee_post_name = employee_post_name;
    }
}
