package com.example.monitoring.repository;

import com.example.monitoring.model.RepairActionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RepairActionTypeRepository extends JpaRepository<RepairActionType, Integer> {
    @Query("select r from RepairActionType r where r.repair_action_type_name = ?1")
    List<RepairActionType> findByRepair_action_type_name(String repair_action_type_name);
}
