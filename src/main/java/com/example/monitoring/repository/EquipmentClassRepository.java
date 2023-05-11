package com.example.monitoring.repository;

import com.example.monitoring.model.EquipmentClass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EquipmentClassRepository extends JpaRepository<EquipmentClass, Integer> {
    @Query("select e from EquipmentClass e where e.equipment_class_name = ?1")
    List<EquipmentClass> findByEquipment_class_name(String equipment_class_name);

}
