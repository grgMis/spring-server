package com.example.monitoring.model;

import jakarta.persistence.*;

@Entity
@Table(name = "action_composition_state")
public class ActionCompositionState {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer action_composition_state_id;

    @Column(name = "action_composition_state_name", nullable = false, length = 40)
    private String action_composition_state_name;

    public ActionCompositionState() {
    }

    public ActionCompositionState(String action_composition_state_name) {
        this.action_composition_state_name = action_composition_state_name;
    }

    public Integer getAction_composition_state_id() {
        return action_composition_state_id;
    }

    public void setAction_composition_state_id(Integer action_composition_state_id) {
        this.action_composition_state_id = action_composition_state_id;
    }

    public String getAction_composition_state_name() {
        return action_composition_state_name;
    }

    public void setAction_composition_state_name(String action_composition_state_name) {
        this.action_composition_state_name = action_composition_state_name;
    }
}
