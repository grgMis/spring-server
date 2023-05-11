package com.example.monitoring.controller;

import com.example.monitoring.model.*;
import com.example.monitoring.repository.DeptRepository;
import com.example.monitoring.repository.DeptTypeRepository;
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
public class DeptController {

    @Autowired
    private DeptRepository deptRepository;

    @Autowired
    private DeptTypeRepository deptTypeRepository;

    @Autowired
    private FieldRepository fieldRepository;

    @GetMapping("/dept")
    public ResponseEntity<List<Dept>> getListDept(@RequestParam(required = false) String dept_name,
                                                  @RequestParam(required = false) Integer dept_type_id,
                                                  @RequestParam(required = false) Integer field_id) throws Exception {

        List<Dept> depts = new ArrayList<>();

        DeptType deptType;
        Field field;

        if (dept_name != null) {
            depts.addAll(deptRepository.findByDept_name(dept_name));
            return new ResponseEntity<>(depts, HttpStatus.OK);
        }

        if (dept_type_id != null) {
            deptType = deptTypeRepository.findById(dept_type_id)
                    .orElseThrow(() -> new Exception("Not found [dept_type] with id = " + dept_type_id));
            depts.addAll(deptRepository.findByDeptType(deptType));
            return new ResponseEntity<>(depts, HttpStatus.OK);
        }

        if (field_id != null) {
            field = fieldRepository.findById(field_id)
                    .orElseThrow(() -> new Exception("Not found [field] with id = " + field_id));
            depts.addAll(deptRepository.findByField(field));
            return new ResponseEntity<>(depts, HttpStatus.OK);
        }

        if (dept_name == null && dept_type_id == null && field_id == null) {
            depts.addAll(deptRepository.findAll());
            return new ResponseEntity<>(depts, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/dept/{dept_id}")
    public ResponseEntity<Dept> getDeptById(@PathVariable("dept_id") Integer dept_id) throws Exception {
        Dept dept = deptRepository.findById(dept_id)
                .orElseThrow(() -> new Exception("Not found [dept] with id = " + dept_id));
        return new ResponseEntity<>(dept, HttpStatus.OK);
    }

    @PostMapping("/dept")
    public ResponseEntity<Dept> createDept(@RequestParam Integer dept_type_id,
                                           @RequestParam Integer field_id,
                                           @RequestBody Dept dept) throws Exception {

        Date date = new Date();

        DeptType deptType = deptTypeRepository.findById(dept_type_id)
                .orElseThrow(() -> new Exception("Not found [dept_type] with id = " + dept_type_id));

        Field field = fieldRepository.findById(field_id)
                .orElseThrow(() -> new Exception("Not found [field] with id = " + field_id));

        dept.setDeptType(deptType);
        dept.setField(field);
        dept.setDate_entry(date);

        deptRepository.save(dept);

        return new ResponseEntity<>(dept, HttpStatus.CREATED);
    }

    @PutMapping("/dept/{dept_id}")
    public ResponseEntity<Dept> updateDept(@PathVariable("dept_id") Integer dept_id,
                                           @RequestParam(required = false) Integer dept_type_id,
                                           @RequestParam(required = false) Integer field_id,
                                           @RequestBody Dept dept) throws Exception {
        Date date = new Date();

        DeptType deptType;
        Field field;


        Dept entity = deptRepository.findById(dept_id)
                .orElseThrow(() -> new Exception("Not found [dept] with id = " + dept_id));

        if (dept_type_id != null) {
            deptType = deptTypeRepository.findById(dept_type_id)
                    .orElseThrow(() -> new Exception("Not found [dep_type] with id = " + dept_type_id));
            entity.setDeptType(deptType);
        }

        if (field_id != null) {
            field = fieldRepository.findById(field_id)
                    .orElseThrow(() -> new Exception("Not found [field] with id = " + field_id));
            entity.setField(field);
        }

        entity.setDept_name(dept.getDept_name());
        entity.setDate_entry(date);

        deptRepository.save(entity);

        return new ResponseEntity<>(entity, HttpStatus.OK);
    }

    @DeleteMapping("/dept/{dept_id}")
    public ResponseEntity<Dept> deleteDept(@PathVariable("dept_id") Integer dept_id) {

        deptRepository.deleteById(dept_id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
