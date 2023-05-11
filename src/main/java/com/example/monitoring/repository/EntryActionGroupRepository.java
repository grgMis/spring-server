package com.example.monitoring.repository;

import com.example.monitoring.model.EntryActionGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EntryActionGroupRepository extends JpaRepository<EntryActionGroup, Integer> {
    @Query("select e from EntryActionGroup e where e.entry_action_group_name = ?1")
    List<EntryActionGroup> findByEntry_action_group_name(String entry_action_group_name);
}
