package com.example.monitoring.controller;

import com.example.monitoring.model.DeptType;
import com.example.monitoring.model.EquipmentCategory;
import com.example.monitoring.repository.DeptTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/api")
public class DeptTypeController {

    @Autowired
    private DeptTypeRepository deptTypeRepository;

    @GetMapping("/dept-type")
    public ResponseEntity<List<DeptType>> getListDeptType(@RequestParam(required = false) String dept_type_name) {
        List<DeptType> deptTypes = new ArrayList<>();

        if (dept_type_name == null) {
            deptTypes.addAll(deptTypeRepository.findAll());
        } else {
            deptTypes.addAll(deptTypeRepository.findByDept_type_name(dept_type_name));
        }

        if (deptTypes.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(deptTypes, HttpStatus.OK);
    }

    @GetMapping("/dept-type/{dept_type_id}")
    public ResponseEntity<DeptType> getDeptTypeById(@PathVariable("dept_type_id") Integer dept_type_id) throws Exception {
        DeptType deptType = deptTypeRepository.findById(dept_type_id)
                .orElseThrow(() -> new Exception("Not found [dept_type] with id = " + dept_type_id));
        return new ResponseEntity<>(deptType, HttpStatus.OK);
    }

    @PostMapping("/dept-type")
    public ResponseEntity<DeptType> createDeptType(@RequestBody DeptType deptType) {
        DeptType entity = deptTypeRepository
                .save(new DeptType(deptType.getDept_type_name()));
        return new ResponseEntity<>(entity, HttpStatus.CREATED);
    }

    @PutMapping("/dept-type/{dept_type_id}")
    public ResponseEntity<DeptType> updateDeptType(@PathVariable("dept_type_id") Integer dept_type_id,
                                                   @RequestBody DeptType deptType) throws Exception {
        DeptType entity = deptTypeRepository.findById(dept_type_id)
                .orElseThrow(() -> new Exception("Not found [dept_type] with id = " + dept_type_id));

        entity.setDept_type_name(deptType.getDept_type_name());

        return new ResponseEntity<>(deptTypeRepository.save(entity), HttpStatus.OK);
    }

    @DeleteMapping("/dept-type/{dept_type_id}")
    public ResponseEntity<HttpStatus> deleteDeptType(@PathVariable("dept_type_id") Integer dept_type_id) {
        deptTypeRepository.deleteById(dept_type_id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
