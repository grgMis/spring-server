package com.example.monitoring.repository;

import com.example.monitoring.model.RepairAction;
import com.example.monitoring.model.RepairActionState;
import com.example.monitoring.model.RepairActionType;
import com.example.monitoring.model.WellEquipment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RepairActionRepository extends JpaRepository<RepairAction, Integer> {
    @Query("select r from RepairAction r where r.repairActionState = ?1")
    List<RepairAction> findByRepairActionState(RepairActionState repairActionState);
    @Query("select r from RepairAction r where r.repairActionType = ?1")
    List<RepairAction> findByRepairActionType(RepairActionType repairActionType);
    @Query("select r from RepairAction r where r.wellEquipment = ?1")
    List<RepairAction> findByWellEquipment(WellEquipment wellEquipment);
}
