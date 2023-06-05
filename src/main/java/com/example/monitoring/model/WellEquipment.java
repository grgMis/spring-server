package com.example.monitoring.model;

import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "well_equipment")
public class WellEquipment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer well_equipment_id;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "well_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Well well;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "equipment_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Equipment equipment;

    public WellEquipment() {
    }

    public Integer getWell_equipment_id() {
        return well_equipment_id;
    }

    public void setWell_equipment_id(Integer well_equipment_id) {
        this.well_equipment_id = well_equipment_id;
    }

    public Well getWell() {
        return well;
    }

    public void setWell(Well well) {
        this.well = well;
    }

    public Equipment getEquipment() {
        return equipment;
    }

    public void setEquipment(Equipment equipment) {
        this.equipment = equipment;
    }
}
