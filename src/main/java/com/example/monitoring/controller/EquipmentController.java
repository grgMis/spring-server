package com.example.monitoring.controller;

import com.example.monitoring.model.Equipment;
import com.example.monitoring.model.EquipmentModel;
import com.example.monitoring.model.EquipmentState;
import com.example.monitoring.repository.EquipmentModelRepository;
import com.example.monitoring.repository.EquipmentRepository;
import com.example.monitoring.repository.EquipmentStateRepository;
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
public class EquipmentController {

    @Autowired
    private EquipmentRepository equipmentRepository;

    @Autowired
    private EquipmentModelRepository equipmentModelRepository;

    @Autowired
    private EquipmentStateRepository equipmentStateRepository;

    @GetMapping("/equipment")
    public ResponseEntity<List<Equipment>> getListEquipment(@RequestParam(required = false) Integer equipment_model_id,
                                                            @RequestParam(required = false) Integer equipment_state_id) {

        try {
            List<Equipment> equipments = new ArrayList<>();

            EquipmentModel equipmentModel;
            EquipmentState equipmentState;

            if (equipment_model_id == null && equipment_state_id == null) {
                equipments.addAll(equipmentRepository.findAll());
            }

            if (equipment_model_id != null) {
                equipmentModel = equipmentModelRepository.findById(equipment_model_id)
                        .orElseThrow(() -> new Exception("Not found [equipment_model] with id = " + equipment_model_id));
                equipments.addAll(equipmentRepository.findByEquipmentModel(equipmentModel));
            }

            if (equipment_state_id != null) {
                equipmentState = equipmentStateRepository.findById(equipment_state_id)
                        .orElseThrow(() -> new Exception("Not found [equipment_state] with id = " + equipment_state_id));
                equipments.addAll(equipmentRepository.findByEquipmentState(equipmentState));
            }

            if (equipments.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(equipments, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/equipment/{equipment_id}")
    public ResponseEntity<Equipment> getEquipmentById(@PathVariable("equipment_id") Integer equipment_id) {

        try {
            Equipment equipment = equipmentRepository.findById(equipment_id)
                    .orElseThrow(() -> new Exception("Not found [equipment] with id = " + equipment_id));

            return new ResponseEntity<>(equipment, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/equipment")
    public ResponseEntity<Equipment> createEquipment(@RequestParam Integer equipment_model_id,
                                                     @RequestParam Integer equipment_state_id,
                                                     @RequestBody Equipment equipment) {

        try {
            Date date = new Date();

            String currentInventoryNumber = equipment.getInventory_number();
            String currentFactoryNumber = equipment.getFactory_number();

            if (equipmentRepository.findByInventory_number(currentInventoryNumber) != null) {
                throw new Exception("Инвентарный номер уже существует");
            }

            if (equipmentRepository.findByFactory_number(currentFactoryNumber) != null) {
                throw new Exception("Заводской номер уже существует");
            }

            EquipmentModel equipmentModel = equipmentModelRepository.findById(equipment_model_id)
                    .orElseThrow(() -> new Exception("Not found [equipment_model] with id = " + equipment_model_id));

            EquipmentState equipmentState = equipmentStateRepository.findById(equipment_state_id)
                    .orElseThrow(() -> new Exception("Not found [equipment_state] with id = " + equipment_state_id));

            equipment.setEquipmentModel(equipmentModel);
            equipment.setEquipmentState(equipmentState);
            equipment.setDate_entry(date);

            equipmentRepository.save(equipment);

            return new ResponseEntity<>(equipment, HttpStatus.CREATED);
        } catch (Exception ex) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/equipment/{equipment_id}")
    public ResponseEntity<Equipment> updateEquipment(@PathVariable("equipment_id") Integer equipment_id,
                                                     @RequestParam(required = false) Integer equipment_model_id,
                                                     @RequestParam(required = false) Integer equipment_state_id,
                                                     @RequestBody(required = false) Equipment equipment) {

        try {
            EquipmentModel equipmentModel;
            EquipmentState equipmentState;

            Equipment entity = equipmentRepository.findById(equipment_id)
                    .orElseThrow(() -> new Exception("Not found [equipment] with id = " + equipment_id));

            if (equipment_model_id != null) {
                equipmentModel = equipmentModelRepository.findById(equipment_model_id)
                        .orElseThrow(() -> new Exception("Not found [equipment_model] with id = " + equipment_model_id));
                entity.setEquipmentModel(equipmentModel);
            }

            if (equipment_state_id != null) {
                equipmentState = equipmentStateRepository.findById(equipment_state_id)
                        .orElseThrow(() -> new Exception("Not found [equipment_state] with id = " + equipment_state_id));
                entity.setEquipmentState(equipmentState);
            }

            if (equipment != null) {
                entity.setFactory_number(equipment.getFactory_number());
                entity.setInventory_number(equipment.getInventory_number());
            }

            equipmentRepository.save(entity);

            return new ResponseEntity<>(entity, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/equipment/{equipment_id}")
    public ResponseEntity<Equipment> deleteEquipment(@PathVariable("equipment_id") Integer equipment_id) {

        try {
            equipmentRepository.findById(equipment_id)
                    .orElseThrow(() -> new Exception("Not found [equipment] with id = " + equipment_id));

            equipmentRepository.deleteById(equipment_id);

            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
