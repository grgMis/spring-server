package com.example.monitoring.model;

import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "equipment_class")
public class EquipmentClass {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer equipment_class_id;

    @Column(name = "equipment_class_name", nullable = false, length = 40)
    private String equipment_class_name;

    @Column(name = "equipment_class_sysname", length = 20)
    private String equipment_class_sysname;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "equipment_category_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private EquipmentCategory equipmentCategory;

    public EquipmentClass() {
    }

    public EquipmentCategory getEquipmentCategory() {
        return equipmentCategory;
    }

    public void setEquipmentCategory(EquipmentCategory equipmentCategory) {
        this.equipmentCategory = equipmentCategory;
    }

    public Integer getEquipment_class_id() {
        return equipment_class_id;
    }

    public void setEquipment_class_id(Integer equipment_class_id) {
        this.equipment_class_id = equipment_class_id;
    }

    public String getEquipment_class_name() {
        return equipment_class_name;
    }

    public void setEquipment_class_name(String equipment_class_name) {
        this.equipment_class_name = equipment_class_name;
    }

    public String getEquipment_class_sysname() {
        return equipment_class_sysname;
    }

    public void setEquipment_class_sysname(String equipment_class_sysname) {
        this.equipment_class_sysname = equipment_class_sysname;
    }
}
