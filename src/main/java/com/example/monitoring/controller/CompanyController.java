package com.example.monitoring.controller;

import com.example.monitoring.model.*;
import com.example.monitoring.repository.CompanyRepository;
import com.example.monitoring.repository.FieldRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/api")
public class CompanyController {

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private FieldRepository fieldRepository;

    @GetMapping("/company")
    public ResponseEntity<List<Company>> getListCompany(@RequestParam(required = false) String company_name,
                                                        @RequestParam(required = false) Integer field_id) throws Exception {

        List<Company> companies = new ArrayList<>();

        Field field;

        if (company_name != null) {
            companies.addAll(companyRepository.findByCompany_name(company_name));
            return new ResponseEntity<>(companies, HttpStatus.OK);
        }

        if (field_id != null) {
            field = fieldRepository.findById(field_id)
                    .orElseThrow(() -> new Exception("Not found [field] with id = " + field_id));
            companies.addAll(companyRepository.findByField(field));
            return new ResponseEntity<>(companies, HttpStatus.OK);
        }

        if (company_name == null && field_id == null) {
            companies.addAll(companyRepository.findAll());
            return new ResponseEntity<>(companies, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/company/{company_id}")
    public ResponseEntity<Company> getCompanyById(@PathVariable("dept_id") Integer company_id) throws Exception {
        Company company = companyRepository.findById(company_id)
                .orElseThrow(() -> new Exception("Not found [company] with id = " + company_id));
        return new ResponseEntity<>(company, HttpStatus.OK);
    }

    @PostMapping("/company")
    public ResponseEntity<Company> createCompany(@RequestParam Integer field_id,
                                                 @RequestBody Company company) throws Exception {

        Date date = new Date();

        Field field = fieldRepository.findById(field_id)
                .orElseThrow(() -> new Exception("Not found [field] with id = " + field_id));

        company.setField(field);
        company.setDate_entry(date);

        companyRepository.save(company);

        return new ResponseEntity<>(company, HttpStatus.CREATED);
    }

    @PutMapping("/company/{company_id}")
    public ResponseEntity<Company> updateCompany(@PathVariable("company_id") Integer company_id,
                                                 @RequestParam(required = false) Integer field_id,
                                                 @RequestBody Company company) throws Exception {
        Date date = new Date();

        Field field;

        Company entity = companyRepository.findById(company_id)
                .orElseThrow(() -> new Exception("Not found [company] with id = " + company_id));

        if (field_id != null) {
            field = fieldRepository.findById(field_id)
                    .orElseThrow(() -> new Exception("Not found [field] with id = " + field_id));
            entity.setField(field);
        }

        entity.setCompany_name(company.getCompany_name());
        entity.setDate_entry(date);

        companyRepository.save(entity);

        return new ResponseEntity<>(entity, HttpStatus.OK);
    }

    @DeleteMapping("/company/{company_id}")
    public ResponseEntity<Company> deleteCompany(@PathVariable("company_id") Integer company_id) {

        companyRepository.deleteById(company_id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
