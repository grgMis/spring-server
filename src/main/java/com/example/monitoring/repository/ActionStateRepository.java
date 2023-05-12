package com.example.monitoring.repository;

import com.example.monitoring.model.ActionState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ActionStateRepository extends JpaRepository<ActionState, Integer> {
    @Query("select a from ActionState a where a.action_state_name = ?1")
    List<ActionState> findByAction_state_name(String action_state_name);

}
