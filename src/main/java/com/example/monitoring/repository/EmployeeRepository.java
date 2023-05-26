package com.example.monitoring.repository;

import com.example.monitoring.model.Employee;
import com.example.monitoring.model.EmployeePost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
    @Query("select e from Employee e where e.employeePost = ?1")
    List<Employee> findByEmployeePost(EmployeePost employeePost);
}
