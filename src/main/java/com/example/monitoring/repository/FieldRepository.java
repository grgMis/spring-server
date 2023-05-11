package com.example.monitoring.repository;

import com.example.monitoring.model.Field;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FieldRepository extends JpaRepository<Field, Integer> {
    @Query("select f from Field f where f.field_name = ?1")
    List<Field> findByField_name(String field_name);
}
