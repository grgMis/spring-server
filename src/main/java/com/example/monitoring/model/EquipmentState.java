package com.example.monitoring.model;

import jakarta.persistence.*;

@Entity
@Table(name = "equipment_state")
public class EquipmentState {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer equipment_state_id;

    @Column(name = "equipment_state_name", nullable = false, length = 20)
    private String equipment_state_name;

    public EquipmentState() {
    }

    public EquipmentState(String equipment_state_name) {
        this.equipment_state_name = equipment_state_name;
    }

    public Integer getEquipment_state_id() {
        return equipment_state_id;
    }

    public void setEquipment_state_id(Integer equipment_state_id) {
        this.equipment_state_id = equipment_state_id;
    }

    public String getEquipment_state_name() {
        return equipment_state_name;
    }

    public void setEquipment_state_name(String equipment_state_name) {
        this.equipment_state_name = equipment_state_name;
    }
}
