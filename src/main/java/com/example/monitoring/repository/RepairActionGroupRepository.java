package com.example.monitoring.repository;

import com.example.monitoring.model.RepairActionGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RepairActionGroupRepository extends JpaRepository<RepairActionGroup, Integer> {
    @Query("select r from RepairActionGroup r where r.repair_action_group_name = ?1")
    List<RepairActionGroup> findByRepair_action_group_name(String repair_action_group_name);
}
