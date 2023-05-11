package com.example.monitoring.repository;

import com.example.monitoring.model.DeptType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DeptTypeRepository extends JpaRepository<DeptType, Integer> {
    @Query("select d from DeptType d where d.dept_type_name = ?1")
    List<DeptType> findByDept_type_name(String dept_type_name);
}
