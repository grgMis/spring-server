package com.example.monitoring.repository;

import com.example.monitoring.model.Company;
import com.example.monitoring.model.Well;
import com.example.monitoring.model.WellState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface WellRepository extends JpaRepository<Well, Integer> {
    @Query("select w from Well w where w.company = ?1")
    List<Well> findByCompany(Company company);
    @Query("select w from Well w where w.wellState = ?1")
    List<Well> findByWellState(WellState wellState);
    @Query("select w from Well w where w.well_name = ?1")
    List<Well> findByWell_name(String well_name);
}
