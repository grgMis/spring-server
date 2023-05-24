package com.example.monitoring.repository;

import com.example.monitoring.model.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRoleRepository extends JpaRepository<UserRole, Integer> {
    @Query("select u from UserRole u where u.user_role_name = ?1")
    List<UserRole> findByUser_role_name(String user_role_name);
}
