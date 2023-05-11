package com.example.monitoring.controller;

import com.example.monitoring.model.*;
import com.example.monitoring.repository.EquipmentRepository;
import com.example.monitoring.repository.WellEquipmentRepository;
import com.example.monitoring.repository.WellRepository;
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
public class WellEquipmentController {

    @Autowired
    private WellEquipmentRepository wellEquipmentRepository;

    @Autowired
    private WellRepository wellRepository;

    @Autowired
    private EquipmentRepository equipmentRepository;

    @GetMapping("/well-equipment")
    public ResponseEntity<List<WellEquipment>> getListWellEquipment(@RequestParam(required = false) Integer well_id,
                                                                    @RequestParam(required = false) Integer equipment_id) throws Exception {

        List<WellEquipment> wellEquipments = new ArrayList<>();

        Well well;
        Equipment equipment;

        if (well_id != null) {
            well = wellRepository.findById(well_id)
                    .orElseThrow(() -> new Exception("Not found [well] with id = " + well_id));
            wellEquipments.addAll(wellEquipmentRepository.findByWell(well));
            return new ResponseEntity<>(wellEquipments, HttpStatus.OK);
        }

        if (equipment_id != null) {
            equipment = equipmentRepository.findById(equipment_id)
                    .orElseThrow(() -> new Exception("Not found [equipment] with id = " + equipment_id));
            wellEquipments.addAll(wellEquipmentRepository.findByEquipment(equipment));
            return new ResponseEntity<>(wellEquipments, HttpStatus.OK);
        }

        if (well_id == null && equipment_id == null) {
            wellEquipments.addAll(wellEquipmentRepository.findAll());
        }

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/well-equipment/{well_equipment_id}")
    public ResponseEntity<WellEquipment> getWellEquipmentById(@PathVariable("well_equipment_id") Integer well_equipment_id) throws Exception {
        WellEquipment wellEquipment = wellEquipmentRepository.findById(well_equipment_id)
                .orElseThrow(() -> new Exception("Not found [well_equipment] with id = " + well_equipment_id));
        return new ResponseEntity<>(wellEquipment, HttpStatus.OK);
    }

    @PostMapping("/well-equipment")
    public ResponseEntity<WellEquipment> createWellEquipment(@RequestParam Integer well_id,
                                                             @RequestParam Integer equipment_id) throws Exception {

        Date date = new Date();

        WellEquipment wellEquipment = new WellEquipment();

        Well well = wellRepository.findById(well_id)
                .orElseThrow(() -> new Exception("Not found [well] with id = " + well_id));

        Equipment equipment = equipmentRepository.findById(equipment_id)
                .orElseThrow(() -> new Exception("Not found [equipment] with id = " + equipment_id));

        wellEquipment.setWell(well);
        wellEquipment.setEquipment(equipment);
        wellEquipment.setDate_entry(date);

        wellEquipmentRepository.save(wellEquipment);

        return new ResponseEntity<>(wellEquipment, HttpStatus.CREATED);
    }

    @PutMapping("/well-equipment/{well_equipment_id}")
    public ResponseEntity<WellEquipment> updateWellEquipment(@PathVariable("well_equipment_id") Integer well_equipment_id,
                                                     @RequestParam(required = false) Integer well_id,
                                                     @RequestParam(required = false) Integer equipment_id) throws Exception {
        Date date = new Date();

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

        entity.setDate_entry(date);

        wellEquipmentRepository.save(entity);

        return new ResponseEntity<>(entity, HttpStatus.OK);
    }

    @DeleteMapping("/well-equipment/{well_equipment_id}")
    public ResponseEntity<Equipment> deleteWellEquipment(@PathVariable("well_equipment_id") Integer well_equipment_id) {

        wellEquipmentRepository.deleteById(well_equipment_id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
