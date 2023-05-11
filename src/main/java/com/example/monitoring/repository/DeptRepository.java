package com.example.monitoring.repository;

import com.example.monitoring.model.Dept;
import com.example.monitoring.model.DeptType;
import com.example.monitoring.model.Field;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DeptRepository extends JpaRepository<Dept, Integer> {
    @Query("select d from Dept d where d.field = ?1")
    List<Dept> findByField(Field field);
    @Query("select d from Dept d where d.deptType = ?1")
    List<Dept> findByDeptType(DeptType deptType);
    @Query("select d from Dept d where d.dept_name = ?1")
    List<Dept> findByDept_name(String dept_name);
}
