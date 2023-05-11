package com.example.monitoring.model;

import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "equipment_model")
public class EquipmentModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer equipment_model_id;

    @Column(name = "equipment_model_name", nullable = false, length = 40)
    private String equipment_model_name;

    @Column(name = "equipment_model_sysname", length = 20)
    private String equipment_model_sysname;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "equipment_class_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private EquipmentClass equipmentClass;

    public EquipmentModel() {
    }

    public EquipmentClass getEquipmentClass() {
        return equipmentClass;
    }

    public void setEquipmentClass(EquipmentClass equipmentClass) {
        this.equipmentClass = equipmentClass;
    }

    public Integer getEquipment_model_id() {
        return equipment_model_id;
    }

    public void setEquipment_model_id(Integer equipment_model_id) {
        this.equipment_model_id = equipment_model_id;
    }

    public String getEquipment_model_name() {
        return equipment_model_name;
    }

    public void setEquipment_model_name(String equipment_model_name) {
        this.equipment_model_name = equipment_model_name;
    }

    public String getEquipment_model_sysname() {
        return equipment_model_sysname;
    }

    public void setEquipment_model_sysname(String equipment_model_sysname) {
        this.equipment_model_sysname = equipment_model_sysname;
    }
}
