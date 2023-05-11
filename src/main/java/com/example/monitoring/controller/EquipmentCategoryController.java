package com.example.monitoring.controller;

import com.example.monitoring.model.EquipmentCategory;
import com.example.monitoring.repository.EquipmentCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/api")
public class EquipmentCategoryController {

    @Autowired
    private EquipmentCategoryRepository equipmentCategoryRepository;

    @GetMapping("/equipment-category")
    public ResponseEntity<List<EquipmentCategory>> getListEquipmentCategories(@RequestParam(required = false) String equipment_category_name) {
        List<EquipmentCategory> equipmentCategories = new ArrayList<>();

        if (equipment_category_name == null) {
            equipmentCategories.addAll(equipmentCategoryRepository.findAll());
        } else {
            equipmentCategories.addAll(equipmentCategoryRepository.findByEquipment_category_name(equipment_category_name));
        }

        if (equipmentCategories.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(equipmentCategories, HttpStatus.OK);
    }

    @GetMapping("/equipment-category/{equipment_category_id}")
    public ResponseEntity<EquipmentCategory> getEquipmentCategoryById(@PathVariable("equipment_category_id") Integer equipment_category_id) throws Exception {
        EquipmentCategory equipmentCategory = equipmentCategoryRepository.findById(equipment_category_id)
                .orElseThrow(() -> new Exception("Not found [equipment_category] with id = " + equipment_category_id));
        return new ResponseEntity<>(equipmentCategory, HttpStatus.OK);
    }

    @PostMapping("/equipment-category")
    public ResponseEntity<EquipmentCategory> createEquipmentCategory(@RequestBody EquipmentCategory equipmentCategory) {
        EquipmentCategory entity = equipmentCategoryRepository
                .save(new EquipmentCategory(equipmentCategory.getEquipment_category_name(), equipmentCategory.getEquipment_category_sysname()));
        return new ResponseEntity<>(entity, HttpStatus.CREATED);
    }

    @PutMapping("/equipment-category/{equipment_category_id}")
    public ResponseEntity<EquipmentCategory> updateEquipmentCategory(@PathVariable("equipment_category_id") Integer equipment_category_id,
                                                                     @RequestBody EquipmentCategory equipmentCategory) throws Exception {
        EquipmentCategory entity = equipmentCategoryRepository.findById(equipment_category_id)
                .orElseThrow(() -> new Exception("Not found [equipment_category] with id = " + equipment_category_id));

        entity.setEquipment_category_name(equipmentCategory.getEquipment_category_name());
        entity.setEquipment_category_sysname(equipmentCategory.getEquipment_category_sysname());

        return new ResponseEntity<>(equipmentCategoryRepository.save(entity), HttpStatus.OK);
    }

    @DeleteMapping("/equipment-category/{equipment_category_id}")
    public ResponseEntity<HttpStatus> deleteEquipmentCategory(@PathVariable("equipment_category_id") Integer equipment_category_id) {
        equipmentCategoryRepository.deleteById(equipment_category_id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
