package com.example.monitoring.model;

import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "employee")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer employee_id;

    @Column(name = "employee_last_name", nullable = false, length = 40)
    private String employee_last_name;

    @Column(name = "employee_first_name", nullable = false, length = 40)
    private String employee_first_name;

    @Column(name = "employee_father_name", nullable = false, length = 40)
    private String employee_father_name;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "employee_post_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private EmployeePost employeePost;

    public Employee() {
    }

    public Employee(String employee_last_name, String employee_first_name, String employee_father_name) {
        this.employee_last_name = employee_last_name;
        this.employee_first_name = employee_first_name;
        this.employee_father_name = employee_father_name;
    }

    public Integer getEmployee_id() {
        return employee_id;
    }

    public void setEmployee_id(Integer employee_id) {
        this.employee_id = employee_id;
    }

    public String getEmployee_last_name() {
        return employee_last_name;
    }

    public void setEmployee_last_name(String employee_last_name) {
        this.employee_last_name = employee_last_name;
    }

    public String getEmployee_first_name() {
        return employee_first_name;
    }

    public void setEmployee_first_name(String employee_first_name) {
        this.employee_first_name = employee_first_name;
    }

    public String getEmployee_father_name() {
        return employee_father_name;
    }

    public void setEmployee_father_name(String employee_father_name) {
        this.employee_father_name = employee_father_name;
    }

    public EmployeePost getEmployeePost() {
        return employeePost;
    }

    public void setEmployeePost(EmployeePost employeePost) {
        this.employeePost = employeePost;
    }
}
