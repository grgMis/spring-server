package com.example.monitoring.repository;

import com.example.monitoring.model.ActionGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ActionGroupRepository extends JpaRepository<ActionGroup, Integer> {
    @Query("select a from ActionGroup a where a.action_group_name = ?1")
    List<ActionGroup> findByAction_group_name(String action_group_name);

}
