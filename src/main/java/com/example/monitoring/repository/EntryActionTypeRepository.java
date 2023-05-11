package com.example.monitoring.repository;

import com.example.monitoring.model.EntryActionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EntryActionTypeRepository extends JpaRepository<EntryActionType, Integer> {
    @Query("select e from EntryActionType e where e.entry_action_type_name = ?1")
    List<EntryActionType> findByEntry_action_type_name(String entry_action_type_name);

}
