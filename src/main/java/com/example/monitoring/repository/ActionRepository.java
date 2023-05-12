package com.example.monitoring.repository;

import com.example.monitoring.model.Action;
import com.example.monitoring.model.ActionState;
import com.example.monitoring.model.ActionType;
import com.example.monitoring.model.WellEquipment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ActionRepository extends JpaRepository<Action, Integer> {
    @Query("select a from Action a where a.actionState = ?1")
    List<Action> findByActionState(ActionState actionState);
    @Query("select a from Action a where a.actionType = ?1")
    List<Action> findByActionType(ActionType actionType);
    @Query("select a from Action a where a.wellEquipment = ?1")
    List<Action> findByWellEquipment(WellEquipment wellEquipment);

}
