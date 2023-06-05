package com.example.monitoring.controller;

import com.example.monitoring.model.*;
import com.example.monitoring.repository.EquipmentCategoryRepository;
import com.example.monitoring.repository.EquipmentRepository;
import com.example.monitoring.repository.WellEquipmentRepository;
import com.example.monitoring.repository.WellRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/api")
public class WellEquipmentController {

    @Autowired
    private WellEquipmentRepository wellEquipmentRepository;

    @Autowired
    private WellRepository wellRepository;

    @Autowired
    private EquipmentRepository equipmentRepository;

    @Autowired
    private EquipmentCategoryRepository equipmentCategoryRepository;

    @GetMapping("/well-equipment")
    public ResponseEntity<List<WellEquipment>> getWellEquipmentList(@RequestParam(required = false) Integer well_id,
                                                                    @RequestParam(required = false) Integer equipment_id) {

        try {
            List<WellEquipment> wellEquipments = new ArrayList<>();

            Well well;
            Equipment equipment;

            if (well_id == null && equipment_id == null) {
                wellEquipments.addAll(wellEquipmentRepository.findAll());
            }

            if (well_id != null) {
                well = wellRepository.findById(well_id)
                        .orElseThrow(() -> new Exception("Not found [well] with id = " + well_id));
                wellEquipments.addAll(wellEquipmentRepository.findByWell(well));
            }

            if (equipment_id != null) {
                equipment = equipmentRepository.findById(equipment_id)
                        .orElseThrow(() -> new Exception("Not found [equipment] with id = " + equipment_id));
                wellEquipments.addAll(wellEquipmentRepository.findByEquipment(equipment));
            }

            if (wellEquipments.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(wellEquipments, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/current-equipment")
    public ResponseEntity<List<WellEquipment>> getWellEquipmentById(@RequestParam Integer well_id,
                                                                    @RequestParam Integer equipment_category_id) {

        try {

            Well well = wellRepository.findById(well_id)
                    .orElseThrow(() -> new Exception("Not found [well] with id = " + well_id));

            EquipmentCategory equipmentCategory = equipmentCategoryRepository.findById(equipment_category_id)
                    .orElseThrow(() -> new Exception("Not found [equipment_category] with id = " + equipment_category_id));

            List<WellEquipment> wellEquipments = new ArrayList<>(wellEquipmentRepository.findByWellAndEquipment_EquipmentModel_EquipmentClass_EquipmentCategory(well, equipmentCategory));

            return new ResponseEntity<>(wellEquipments, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/well-equipment/{well_equipment_id}")
    public ResponseEntity<WellEquipment> getWellEquipmentById(@PathVariable("well_equipment_id") Integer well_equipment_id) {

        try {
            WellEquipment wellEquipment = wellEquipmentRepository.findById(well_equipment_id)
                    .orElseThrow(() -> new Exception("Not found [well_equipment] with id = " + well_equipment_id));

            return new ResponseEntity<>(wellEquipment, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/well-equipment")
    public ResponseEntity<WellEquipment> createWellEquipment(@RequestParam Integer well_id,
                                                             @RequestParam Integer equipment_id) {

        try {
            WellEquipment entity = new WellEquipment();

            Well well = wellRepository.findById(well_id)
                    .orElseThrow(() -> new Exception("Not found [well] with id = " + well_id));

            Equipment equipment = equipmentRepository.findById(equipment_id)
                    .orElseThrow(() -> new Exception("Not found [equipment] with id = " + equipment_id));

            entity.setWell(well);
            entity.setEquipment(equipment);

            wellEquipmentRepository.save(entity);

            return new ResponseEntity<>(entity, HttpStatus.CREATED);
        } catch (Exception ex) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/well-equipment/{well_equipment_id}")
    public ResponseEntity<WellEquipment> updateWellEquipment(@PathVariable("well_equipment_id") Integer well_equipment_id,
                                                             @RequestParam(required = false) Integer well_id,
                                                             @RequestParam(required = false) Integer equipment_id) {

        try {
            Well well;
            Equipment equipment;

            WellEquipment entity = wellEquipmentRepository.findById(well_equipment_id)
                    .orElseThrow(() -> new Exception("Not found [well_equipment] with id = " + well_equipment_id));

            if (well_id != null) {
                well = wellRepository.findById(well_id)
                        .orElseThrow(() -> new Exception("Not found [well] with id = " + well_id));
                entity.setWell(well);
            }

            if (equipment_id != null) {
                equipment = equipmentRepository.findById(equipment_id)
                        .orElseThrow(() -> new Exception("Not found [equipment] with id = " + equipment_id));
                entity.setEquipment(equipment);
            }

            wellEquipmentRepository.save(entity);

            return new ResponseEntity<>(entity, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/well-equipment")
    public ResponseEntity<WellEquipment> deleteWellEquipment(@RequestParam(required = false) Integer well_id,
                                                             @RequestParam(required = false) Integer equipment_id) {

        try {
            Well well;
            Equipment equipment;

            well = wellRepository.findById(well_id)
                    .orElseThrow(() -> new Exception("Not found [well] with id = " + well_id));

            equipment = equipmentRepository.findById(equipment_id)
                        .orElseThrow(() -> new Exception("Not found [equipment] with id = " + equipment_id));

            wellEquipmentRepository.deleteByWellAndEquipment(well, equipment);

            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
