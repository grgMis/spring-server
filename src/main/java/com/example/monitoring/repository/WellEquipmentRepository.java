package com.example.monitoring.repository;

import com.example.monitoring.model.Equipment;
import com.example.monitoring.model.EquipmentCategory;
import com.example.monitoring.model.Well;
import com.example.monitoring.model.WellEquipment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface WellEquipmentRepository extends JpaRepository<WellEquipment, Integer> {
    @Transactional
    @Modifying
    @Query("delete from WellEquipment w where w.well = ?1 and w.equipment = ?2")
    int deleteByWellAndEquipment(Well well, Equipment equipment);
    @Query("""
            select w from WellEquipment w
            where w.well = ?1 and w.equipment.equipmentModel.equipmentClass.equipmentCategory = ?2""")
    List<WellEquipment> findByWellAndEquipment_EquipmentModel_EquipmentClass_EquipmentCategory(Well well, EquipmentCategory equipmentCategory);
    @Query("select w from WellEquipment w where w.equipment = ?1")
    List<WellEquipment> findByEquipment(Equipment equipment);
    @Query("select w from WellEquipment w where w.well = ?1")
    List<WellEquipment> findByWell(Well well);
}
