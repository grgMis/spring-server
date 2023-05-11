package com.example.monitoring.repository;

import com.example.monitoring.model.WellState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface WellStateRepository extends JpaRepository<WellState, Integer> {
    @Query("select w from WellState w where w.well_state_name = ?1")
    List<WellState> findByWell_state_name(String well_state_name);
}
