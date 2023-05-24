package com.example.monitoring.repository;

import com.example.monitoring.model.ActionCompositionState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ActionCompositionStateRepository extends JpaRepository<ActionCompositionState, Integer> {
    @Query("select a from ActionCompositionState a where a.action_composition_state_name = ?1")
    List<ActionCompositionState> findByAction_composition_state_name(String action_composition_state_name);
}
