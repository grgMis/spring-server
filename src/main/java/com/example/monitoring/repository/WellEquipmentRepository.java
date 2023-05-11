package com.example.monitoring.repository;

import com.example.monitoring.model.Equipment;
import com.example.monitoring.model.Well;
import com.example.monitoring.model.WellEquipment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface WellEquipmentRepository extends JpaRepository<WellEquipment, Integer> {
    @Query("select w from WellEquipment w where w.equipment = ?1")
    List<WellEquipment> findByEquipment(Equipment equipment);
    @Query("select w from WellEquipment w where w.well = ?1")
    List<WellEquipment> findByWell(Well well);
}
