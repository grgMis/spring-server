package com.example.monitoring.controller;

import com.example.monitoring.model.Employee;
import com.example.monitoring.model.EmployeePost;
import com.example.monitoring.repository.EmployeePostRepository;
import com.example.monitoring.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/api")
public class EmployeeController {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private EmployeePostRepository employeePostRepository;

    @GetMapping("/employee")
    public ResponseEntity<List<Employee>> getListEmployee(@RequestParam(required = false) Integer employee_post_id) throws Exception {

        List<Employee> employees = new ArrayList<>();
        EmployeePost employeePost;

        if (employee_post_id != null) {
            employeePost = employeePostRepository.findById(employee_post_id)
                    .orElseThrow(() -> new Exception("Not found [employee_post] with id = " + employee_post_id));
            employees.addAll(employeeRepository.findByEmployeePost(employeePost));
        } else {
            employees.addAll(employeeRepository.findAll());
        }

        if (employees.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(employees, HttpStatus.OK);
    }

    @GetMapping("/employee/{employee_id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable("employee_id") Integer employee_id) throws Exception {
        Employee employee = employeeRepository.findById(employee_id)
                .orElseThrow(() -> new Exception("Not found [employee] with id = " + employee_id));
        return new ResponseEntity<>(employee, HttpStatus.OK);
    }

    @PostMapping("/employee")
    public ResponseEntity<Employee> createEmployee(@RequestParam Integer employee_post_id,
                                                   @RequestBody Employee employee) throws Exception {
        EmployeePost employeePost = employeePostRepository.findById(employee_post_id)
                .orElseThrow(() -> new Exception("Not found [employee_post] with id = " + employee_post_id));

        employee.setEmployeePost(employeePost);

        return new ResponseEntity<>(employee, HttpStatus.CREATED);
    }

    @PutMapping("/employee/{employee_id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable("employee_id") Integer employee_id,
                                                   @RequestParam(required = false) Integer employee_post_id,
                                                   @RequestBody Employee employee) throws Exception {
        Employee entity;

        entity = employeeRepository.findById(employee_id)
                .orElseThrow(() -> new Exception("Not found [employee] with id = " + employee_id));

        if (employee_post_id != null) {
            EmployeePost employeePost = employeePostRepository.findById(employee_post_id)
                    .orElseThrow(() -> new Exception("Not found [employee_post] with id = " + employee_post_id));

            entity.setEmployeePost(employeePost);
        }

        entity.setEmployee_last_name(employee.getEmployee_last_name());
        entity.setEmployee_first_name(employee.getEmployee_first_name());
        entity.setEmployee_father_name(employee.getEmployee_father_name());

        return new ResponseEntity<>(employeeRepository.save(entity), HttpStatus.OK);
    }

    @DeleteMapping("/employee/{employee_id}")
    public ResponseEntity<Employee> deleteEmployee(@PathVariable("employee_id") Integer employee_id) {
        employeeRepository.deleteById(employee_id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
