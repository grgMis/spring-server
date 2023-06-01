package com.example.monitoring.controller;

import com.example.monitoring.model.EmployeePost;
import com.example.monitoring.repository.EmployeePostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/api")
public class EmployeePostController {

    @Autowired
    private EmployeePostRepository employeePostRepository;

    @GetMapping("/employee-post")
    public ResponseEntity<List<EmployeePost>> getListEmployeePost(@RequestParam(required = false) String employee_post_name) {

        try {
            List<EmployeePost> employeePosts = new ArrayList<>();

            if (employee_post_name == null) {
                employeePosts.addAll(employeePostRepository.findAll());
            } else {
                employeePosts.addAll(employeePostRepository.findByEmployee_post_name(employee_post_name));
            }

            if (employeePosts.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(employeePosts, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/employee-post/{employee_post_id}")
    public ResponseEntity<EmployeePost> getEmployeePostById(@PathVariable("employee_post_id") Integer employee_post_id) {

        try {
            EmployeePost employeePost = employeePostRepository.findById(employee_post_id)
                    .orElseThrow(() -> new Exception("Not found [employee_post] with id = " + employee_post_id));
            return new ResponseEntity<>(employeePost, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/employee-post")
    public ResponseEntity<EmployeePost> createEmployeePost(@RequestBody EmployeePost employeePost) {

        try {
            EmployeePost entity = employeePostRepository
                    .save(new EmployeePost(employeePost.getEmployee_post_name()));
            return new ResponseEntity<>(entity, HttpStatus.CREATED);
        } catch (Exception ex) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/employee-post/{employee_post_id}")
    public ResponseEntity<EmployeePost> updateEmployeePost(@PathVariable("employee_post_id") Integer employee_post_id,
                                                           @RequestBody EmployeePost employeePost) {

        try {
            EmployeePost entity = employeePostRepository.findById(employee_post_id)
                    .orElseThrow(() -> new Exception("Not found [employee_post] with id = " + employee_post_id));

            entity.setEmployee_post_name(employeePost.getEmployee_post_name());

            employeePostRepository.save(entity);

            return new ResponseEntity<>(entity, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/employee-post/{employee_post_id}")
    public ResponseEntity<HttpStatus> deleteEmployeePost(@PathVariable("employee_post_id") Integer employee_post_id) {

        try {
            employeePostRepository.findById(employee_post_id)
                    .orElseThrow(() -> new Exception("Not found [employee_post] with id = " + employee_post_id));

            employeePostRepository.deleteById(employee_post_id);

            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
