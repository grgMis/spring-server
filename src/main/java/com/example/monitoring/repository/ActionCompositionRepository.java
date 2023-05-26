package com.example.monitoring.repository;

import com.example.monitoring.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ActionCompositionRepository extends JpaRepository<ActionComposition, Integer> {
    @Query("select a from ActionComposition a where a.actionCompositionState = ?1")
    List<ActionComposition> findByActionCompositionState(ActionCompositionState actionCompositionState);
    @Query("select a from ActionComposition a where a.equipment = ?1")
    List<ActionComposition> findByEquipment(Equipment equipment);
    @Query("select a from ActionComposition a where a.action = ?1")
    List<ActionComposition> findByAction(Action action);
}
