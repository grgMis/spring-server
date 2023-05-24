package com.example.monitoring.repository;

import com.example.monitoring.model.Company;
import com.example.monitoring.model.Field;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CompanyRepository extends JpaRepository<Company, Integer> {
    @Query("select c from Company c where c.company_name = ?1")
    List<Company> findByCompany_name(String company_name);
    @Query("select d from Dept d where d.field = ?1")
    List<Company> findByField(Field field);
}
