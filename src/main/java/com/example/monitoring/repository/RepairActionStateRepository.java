package com.example.monitoring.repository;

import com.example.monitoring.model.RepairActionState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RepairActionStateRepository extends JpaRepository<RepairActionState, Integer> {
    @Query("select r from RepairActionState r where r.repair_action_state_name = ?1")
    List<RepairActionState> findByRepair_action_state_name(String repair_action_state_name);
}
