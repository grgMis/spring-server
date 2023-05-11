package com.example.monitoring.repository;

import com.example.monitoring.model.EntryActionState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EntryActionStateRepository extends JpaRepository<EntryActionState, Integer> {
    @Query("select e from EntryActionState e where e.entry_action_state_name = ?1")
    List<EntryActionState> findByEntry_action_state_name(String entry_action_state_name);
}
