package com.example.monitoring.controller;

import com.example.monitoring.model.EquipmentState;
import com.example.monitoring.repository.EquipmentStateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/api")
public class EquipmentStateController {

    @Autowired
    private EquipmentStateRepository equipmentStateRepository;

    @GetMapping("/equipment-state")
    public ResponseEntity<List<EquipmentState>> getListEquipmentState(@RequestParam(required = false) String equipment_state_name) {

        try {
            List<EquipmentState> equipmentStates = new ArrayList<>();

            if (equipment_state_name == null) {
                equipmentStates.addAll(equipmentStateRepository.findAll());
            } else {
                equipmentStates.addAll(equipmentStateRepository.findByEquipment_state_name(equipment_state_name));
            }

            if (equipmentStates.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(equipmentStates, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/equipment-state/{equipment_state_id}")
    public ResponseEntity<EquipmentState> getEquipmentStateById(@PathVariable("equipment_state_id") Integer equipment_state_id) {

        try {
            EquipmentState equipmentState = equipmentStateRepository.findById(equipment_state_id)
                    .orElseThrow(() -> new Exception("Not found [equipment_state] with id = " + equipment_state_id));

            return new ResponseEntity<>(equipmentState, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/equipment-state")
    public ResponseEntity<EquipmentState> createEquipmentState(@RequestBody EquipmentState equipmentState) {

        try {
            EquipmentState entity = equipmentStateRepository
                    .save(new EquipmentState(equipmentState.getEquipment_state_name()));

            return new ResponseEntity<>(entity, HttpStatus.CREATED);
        } catch (Exception ex) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/equipment-state/{equipment_state_id}")
    public ResponseEntity<EquipmentState> updateEquipmentState(@PathVariable("equipment_state_id") Integer equipment_state_id,
                                                               @RequestBody EquipmentState equipmentState) {

        try {
            EquipmentState entity = equipmentStateRepository.findById(equipment_state_id)
                    .orElseThrow(() -> new Exception("Not found [equipment_state] with id = " + equipment_state_id));

            entity.setEquipment_state_name(equipmentState.getEquipment_state_name());

            equipmentStateRepository.save(entity);

            return new ResponseEntity<>(entity, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/equipment-state/{equipment_state_id}")
    public ResponseEntity<HttpStatus> deleteEquipmentState(@PathVariable("equipment_state_id") Integer equipment_state_id) {

        try {
            equipmentStateRepository.findById(equipment_state_id)
                    .orElseThrow(() -> new Exception("Not found [equipment_state] with id = " + equipment_state_id));

            equipmentStateRepository.deleteById(equipment_state_id);

            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
