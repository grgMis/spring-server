package com.example.monitoring.model;

import jakarta.persistence.*;

@Entity
@Table(name = "field")
public class Field {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer field_id;

    @Column(name = "field_name", nullable = false, length = 40)
    private String field_name;

    public Field() {
    }

    public Field(String field_name) {
        this.field_name = field_name;
    }

    public Integer getField_id() {
        return field_id;
    }

    public void setField_id(Integer field_id) {
        this.field_id = field_id;
    }

    public String getField_name() {
        return field_name;
    }

    public void setField_name(String field_name) {
        this.field_name = field_name;
    }
}
