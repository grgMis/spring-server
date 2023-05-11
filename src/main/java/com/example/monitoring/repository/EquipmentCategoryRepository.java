package com.example.monitoring.repository;

import com.example.monitoring.model.EquipmentCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EquipmentCategoryRepository extends JpaRepository<EquipmentCategory, Integer> {
    @Query("select e from EquipmentCategory e where e.equipment_category_name = ?1")
    List<EquipmentCategory> findByEquipment_category_name(String name);
}
