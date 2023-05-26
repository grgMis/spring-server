package com.example.monitoring.repository;

import com.example.monitoring.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ActionRepository extends JpaRepository<Action, Integer> {
    @Query("select a from Action a where a.user = ?1")
    List<Action> findByUser(User user);
    @Query("select a from Action a where a.actionType = ?1")
    List<Action> findByActionType(ActionType actionType);
    @Query("select a from Action a where a.well = ?1")
    List<Action> findByWell(Well well);
    @Query("select a from Action a where a.actionState = ?1")
    List<Action> findByActionState(ActionState actionState);

}
