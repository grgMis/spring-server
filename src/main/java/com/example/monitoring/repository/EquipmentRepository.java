package com.example.monitoring.repository;

import com.example.monitoring.model.Equipment;
import com.example.monitoring.model.EquipmentModel;
import com.example.monitoring.model.EquipmentState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EquipmentRepository extends JpaRepository<Equipment, Integer> {
    @Query("select e from Equipment e where e.equipmentState = ?1")
    List<Equipment> findByEquipmentState(EquipmentState equipmentState);
    @Query("select e from Equipment e where e.equipmentModel = ?1")
    List<Equipment> findByEquipmentModel(EquipmentModel equipmentModel);
}
