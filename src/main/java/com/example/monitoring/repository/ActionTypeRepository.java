package com.example.monitoring.repository;

import com.example.monitoring.model.ActionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ActionTypeRepository extends JpaRepository<ActionType, Integer> {
    @Query("select a from ActionType a where a.action_type_name = ?1")
    List<ActionType> findByAction_type_name(String action_type_name);


}
