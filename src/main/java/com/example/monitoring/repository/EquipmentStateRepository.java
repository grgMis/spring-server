package com.example.monitoring.repository;

import com.example.monitoring.model.EquipmentState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EquipmentStateRepository extends JpaRepository<EquipmentState, Integer> {
    @Query("select e from EquipmentState e where e.equipment_state_name = ?1")
    List<EquipmentState> findByEquipment_state_name(String equipment_state_name);
}
