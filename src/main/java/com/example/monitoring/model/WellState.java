package com.example.monitoring.model;

import jakarta.persistence.*;

@Entity
@Table(name = "well_state")
public class WellState {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer well_state_id;

    @Column(name = "well_state_name", nullable = false, length = 40)
    private String well_state_name;

    public WellState() {
    }

    public WellState(String well_state_name) {
        this.well_state_name = well_state_name;
    }

    public Integer getWell_state_id() {
        return well_state_id;
    }

    public void setWell_state_id(Integer well_state_id) {
        this.well_state_id = well_state_id;
    }

    public String getWell_state_name() {
        return well_state_name;
    }

    public void setWell_state_name(String well_state_name) {
        this.well_state_name = well_state_name;
    }
}
