package com.example.monitoring.repository;

import com.example.monitoring.model.Employee;
import com.example.monitoring.model.EmployeePost;
import com.example.monitoring.model.User;
import com.example.monitoring.model.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer> {
    @Query("select u from User u where u.employee.employeePost = ?1")
    List<User> findByEmployee_EmployeePost(EmployeePost employeePost);
    @Query("select u from User u where u.user_login = ?1")
    User findByUser_login(String user_login);
    @Query("select u from User u where u.employee = ?1")
    List<User> findByEmployee(Employee employee);
    @Query("select u from User u where u.userRole = ?1")
    List<User> findByUserRole(UserRole userRole);
}
