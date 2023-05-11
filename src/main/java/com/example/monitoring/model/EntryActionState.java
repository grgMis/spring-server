package com.example.monitoring.model;

import jakarta.persistence.*;

@Entity
@Table(name = "entry_action_state")
public class EntryActionState {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer entry_action_state_id;

    @Column(name = "entry_action_state_name", nullable = false, length = 20)
    private String entry_action_state_name;

    public EntryActionState() {
    }

    public EntryActionState(String entry_action_state_name) {
        this.entry_action_state_name = entry_action_state_name;
    }

    public Integer getEntry_action_state_id() {
        return entry_action_state_id;
    }

    public void setEntry_action_state_id(Integer entry_action_state_id) {
        this.entry_action_state_id = entry_action_state_id;
    }

    public String getEntry_action_state_name() {
        return entry_action_state_name;
    }

    public void setEntry_action_state_name(String entry_action_state_name) {
        this.entry_action_state_name = entry_action_state_name;
    }
}
