package com.example.monitoring.repository;

import com.example.monitoring.model.EmployeePost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EmployeePostRepository extends JpaRepository<EmployeePost, Integer> {
    @Query("select e from EmployeePost e where e.employee_post_name = ?1")
    List<EmployeePost> findByEmployee_post_name(String employee_post_name);
}
