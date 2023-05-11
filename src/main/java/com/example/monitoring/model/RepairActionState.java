package com.example.monitoring.model;

import jakarta.persistence.*;

@Entity
@Table(name = "repair_action_state")
public class RepairActionState {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer repair_action_state;

    @Column(name = "repair_action_state_name", nullable = false, length = 20)
    private String repair_action_state_name;

    public RepairActionState() {
    }

    public RepairActionState(String repair_action_state_name) {
        this.repair_action_state_name = repair_action_state_name;
    }

    public Integer getRepair_action_state() {
        return repair_action_state;
    }

    public void setRepair_action_state(Integer repair_action_state) {
        this.repair_action_state = repair_action_state;
    }

    public String getRepair_action_state_name() {
        return repair_action_state_name;
    }

    public void setRepair_action_state_name(String repair_action_state_name) {
        this.repair_action_state_name = repair_action_state_name;
    }
}
