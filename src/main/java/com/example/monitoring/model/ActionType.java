package com.example.monitoring.model;

import jakarta.persistence.*;

@Entity
@Table(name = "action_type")
public class ActionType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer action_type_id;

    @Column(name = "action_type_name", nullable = false, length = 50)
    private String action_type_name;

    public ActionType() {
    }

    public ActionType(String action_type_name) {
        this.action_type_name = action_type_name;
    }

    public Integer getAction_type_id() {
        return action_type_id;
    }

    public void setAction_type_id(Integer action_type_id) {
        this.action_type_id = action_type_id;
    }

    public String getAction_type_name() {
        return action_type_name;
    }

    public void setAction_type_name(String action_type_name) {
        this.action_type_name = action_type_name;
    }
}
