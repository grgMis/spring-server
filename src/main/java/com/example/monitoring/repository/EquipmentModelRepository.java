package com.example.monitoring.repository;

import com.example.monitoring.model.EquipmentClass;
import com.example.monitoring.model.EquipmentModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EquipmentModelRepository extends JpaRepository<EquipmentModel, Integer> {
    @Query("select e from EquipmentModel e where e.equipmentClass = ?1")
    List<EquipmentModel> findByEquipmentClass(EquipmentClass equipmentClass);
    @Query("select e from EquipmentModel e where e.equipment_model_name = ?1")
    List<EquipmentModel> findByEquipment_model_name(String equipment_model_name);
}
