package com.example.monitoring.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Entity
@Table(name = "well_equipment")
public class WellEquipment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer well_equipment_id;

    @Column(name = "date_entry", nullable = false)
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @JsonFormat(pattern = "dd-MM-yyyy", shape = JsonFormat.Shape.STRING)
    private Date date_entry;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "well_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Well well;

    @OneToOne(fetch = FetchType.EAGER, optional = false)
    @MapsId
    @JoinColumn(name = "equipment_id", nullable = false)
    private Equipment equipment;

    public WellEquipment() {
    }

    public Integer getWell_equipment_id() {
        return well_equipment_id;
    }

    public void setWell_equipment_id(Integer well_equipment_id) {
        this.well_equipment_id = well_equipment_id;
    }

    public Date getDate_entry() {
        return date_entry;
    }

    public void setDate_entry(Date date) {
        this.date_entry = date;
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
