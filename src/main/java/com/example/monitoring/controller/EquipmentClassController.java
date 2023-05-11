package com.example.monitoring.controller;

import com.example.monitoring.model.EquipmentCategory;
import com.example.monitoring.model.EquipmentClass;
import com.example.monitoring.repository.EquipmentCategoryRepository;
import com.example.monitoring.repository.EquipmentClassRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/api")
public class EquipmentClassController {

    @Autowired
    private EquipmentClassRepository equipmentClassRepository;

    @Autowired
    private EquipmentCategoryRepository equipmentCategoryRepository;

    @GetMapping("/equipment-class")
    public ResponseEntity<List<EquipmentClass>> getListEquipmentClass(@RequestParam(required = false) String equipment_class_name) {
        List<EquipmentClass> equipmentClasses = new ArrayList<>();

        if (equipment_class_name == null) {
            equipmentClasses.addAll(equipmentClassRepository.findAll());
        } else {
            equipmentClasses.addAll(equipmentClassRepository.findByEquipment_class_name(equipment_class_name));
        }

        if (equipmentClasses.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(equipmentClasses, HttpStatus.OK);
    }

    @GetMapping("/equipment-class/{equipment_class_id}")
    public ResponseEntity<EquipmentClass> getEquipmentCategoryById(@PathVariable("equipment_class_id") Integer equipment_class_id) throws Exception {
        EquipmentClass equipmentClass = equipmentClassRepository.findById(equipment_class_id)
                .orElseThrow(() -> new Exception("Not found [equipment_class] with id = " + equipment_class_id));
        return new ResponseEntity<>(equipmentClass, HttpStatus.OK);
    }

    @PostMapping("/equipment-class")
    public ResponseEntity<EquipmentClass> createEquipmentClass(@RequestParam Integer equipment_category_id,
                                                               @RequestBody EquipmentClass equipmentClass) throws Exception {
        EquipmentClass entity = equipmentCategoryRepository.findById(equipment_category_id).map(equipmentCategory -> {
            equipmentClass.setEquipmentCategory(equipmentCategory);
            return equipmentClassRepository.save(equipmentClass);
        }).orElseThrow(() -> new Exception("Not found [equipment_category] with id = " + equipment_category_id));

        return new ResponseEntity<>(entity, HttpStatus.CREATED);
    }

    @PutMapping("/equipment-class/{equipment_class_id}")
    public ResponseEntity<EquipmentClass> updateEquipmentClass(@PathVariable("equipment_class_id") Integer equipment_class_id,
                                                               @RequestParam(required = false) Integer equipment_category_id,
                                                               @RequestBody EquipmentClass equipmentClass) throws Exception {
        EquipmentClass entity;

        entity = equipmentClassRepository.findById(equipment_class_id)
                .orElseThrow(() -> new Exception("Not found [equipment_class_id] with id = " + equipment_class_id));

        if (equipment_category_id != null) {
            EquipmentCategory equipmentCategory = equipmentCategoryRepository.findById(equipment_category_id)
                    .orElseThrow(() -> new Exception("Not found [equipment_category] with id = " + equipment_category_id));

            entity.setEquipmentCategory(equipmentCategory);
        }
        entity.setEquipment_class_name(equipmentClass.getEquipment_class_name());
        entity.setEquipment_class_sysname(equipmentClass.getEquipment_class_sysname());

        return new ResponseEntity<>(equipmentClassRepository.save(entity), HttpStatus.OK);
    }

    @DeleteMapping("/equipment-class/{equipment_class_id}")
    public ResponseEntity<EquipmentClass> deleteEquipmentClass(@PathVariable("equipment_class_id") Integer equipment_class_id) {
        equipmentClassRepository.deleteById(equipment_class_id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
