package com.example.monitoring.model;

import jakarta.persistence.*;

@Entity
@Table(name = "dept_type")
public class DeptType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer dept_type_id;

    @Column(name = "dept_type_name", nullable = false, length = 40)
    private String dept_type_name;

    public DeptType() {
    }

    public DeptType(String dept_type_name) {
        this.dept_type_name = dept_type_name;
    }

    public Integer getDept_type_id() {
        return dept_type_id;
    }

    public void setDept_type_id(Integer dept_type_id) {
        this.dept_type_id = dept_type_id;
    }

    public String getDept_type_name() {
        return dept_type_name;
    }

    public void setDept_type_name(String dept_type_name) {
        this.dept_type_name = dept_type_name;
    }


}
