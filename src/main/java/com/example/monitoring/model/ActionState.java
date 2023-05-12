package com.example.monitoring.model;

import jakarta.persistence.*;

@Entity
@Table(name = "action_state")
public class ActionState {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer action_state;

    @Column(name = "action_state_name", nullable = false, length = 20)
    private String action_state_name;

    public ActionState() {
    }

    public ActionState(String action_state_name) {
        this.action_state_name = action_state_name;
    }

    public Integer getAction_state() {
        return action_state;
    }

    public void setAction_state(Integer action_state) {
        this.action_state = action_state;
    }

    public String getAction_state_name() {
        return action_state_name;
    }

    public void setAction_state_name(String action_state_name) {
        this.action_state_name = action_state_name;
    }
}
