package com.example.monitoring.model;

import jakarta.persistence.*;

@Entity
@Table(name = "equipment_category")
public class EquipmentCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer equipment_category_id;

    @Column(name = "equipment_category_name", nullable = false, length = 40)
    private String equipment_category_name;

    @Column(name = "equipment_category_sysname", length = 20)
    private String equipment_category_sysname;

    public EquipmentCategory() {}

    public EquipmentCategory(String equipment_category_name, String equipment_category_sysname) {
        this.equipment_category_name = equipment_category_name;
        this.equipment_category_sysname = equipment_category_sysname;
    }

    public Integer getEquipment_category_id() {
        return equipment_category_id;
    }

    public void setEquipment_category_id(Integer equipment_category_id) {
        this.equipment_category_id = equipment_category_id;
    }

    public String getEquipment_category_name() {
        return equipment_category_name;
    }

    public void setEquipment_category_name(String equipment_category_name) {
        this.equipment_category_name = equipment_category_name;
    }

    public String getEquipment_category_sysname() {
        return equipment_category_sysname;
    }

    public void setEquipment_category_sysname(String equipment_category_sysname) {
        this.equipment_category_sysname = equipment_category_sysname;
    }
}
