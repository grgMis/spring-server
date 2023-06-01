package com.example.monitoring.controller;

import com.example.monitoring.model.EquipmentClass;
import com.example.monitoring.model.EquipmentModel;
import com.example.monitoring.repository.EquipmentClassRepository;
import com.example.monitoring.repository.EquipmentModelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/api")
public class EquipmentModelController {

    @Autowired
    private EquipmentModelRepository equipmentModelRepository;

    @Autowired
    private EquipmentClassRepository equipmentClassRepository;

    @GetMapping("/equipment-model")
    public ResponseEntity<List<EquipmentModel>> getListEquipmentModel(@RequestParam(required = false) String equipment_model_name,
                                                                      @RequestParam(required = false) Integer equipment_class_id) {

        try {
            List<EquipmentModel> equipmentModels = new ArrayList<>();

            EquipmentClass equipmentClass;

            if (equipment_model_name == null && equipment_class_id == null) {
                equipmentModels.addAll(equipmentModelRepository.findAll());
            }

            if (equipment_model_name != null) {
                equipmentModels.addAll(equipmentModelRepository.findByEquipment_model_name(equipment_model_name));
            }

            if (equipment_class_id != null) {
                equipmentClass = equipmentClassRepository.findById(equipment_class_id)
                        .orElseThrow(() -> new Exception("Not found [equipment_class] with id = " + equipment_class_id));
                equipmentModels.addAll(equipmentModelRepository.findByEquipmentClass(equipmentClass));
            }

            if (equipmentModels.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(equipmentModels, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/equipment-model/{equipment_model_id}")
    public ResponseEntity<EquipmentModel> getEquipmentModelById(@PathVariable("equipment_model_id") Integer equipment_model_id) {

        try {
            EquipmentModel equipmentModel = equipmentModelRepository.findById(equipment_model_id)
                    .orElseThrow(() -> new Exception("Not found [equipment_model] with id = " + equipment_model_id));

            return new ResponseEntity<>(equipmentModel, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/equipment-model")
    public ResponseEntity<EquipmentModel> createEquipmentModel(@RequestParam Integer equipment_class_id,
                                                               @RequestBody EquipmentModel equipmentModel) {

        try {
            EquipmentModel entity = equipmentClassRepository.findById(equipment_class_id).map(equipmentClass -> {
                equipmentModel.setEquipmentClass(equipmentClass);
                return equipmentModelRepository.save(equipmentModel);
            }).orElseThrow(() -> new Exception("Not found [equipment_class] with id = " + equipment_class_id));

            return new ResponseEntity<>(entity, HttpStatus.CREATED);
        } catch (Exception ex) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/equipment-model/{equipment_model_id}")
    public ResponseEntity<EquipmentModel> updateEquipmentModel(@PathVariable("equipment_model_id") Integer equipment_model_id,
                                                               @RequestParam(required = false) Integer equipment_class_id,
                                                               @RequestBody EquipmentModel equipmentModel) {

        try {
            EquipmentModel entity;

            entity = equipmentModelRepository.findById(equipment_model_id)
                    .orElseThrow(() -> new Exception("Not found [equipment_model_id] with id = " + equipment_model_id));

            if (equipment_class_id != null) {
                EquipmentClass equipmentClass = equipmentClassRepository.findById(equipment_class_id)
                        .orElseThrow(() -> new Exception("Not found [equipment_class] with id = " + equipment_class_id));

                entity.setEquipmentClass(equipmentClass);
            }
            entity.setEquipment_model_name(equipmentModel.getEquipment_model_name());

            equipmentModelRepository.save(entity);

            return new ResponseEntity<>(entity, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/equipment-model/{equipment_model_id}")
    public ResponseEntity<EquipmentModel> deleteEquipmentModel(@PathVariable("equipment_model_id") Integer equipment_model_id) {

        try {
            equipmentModelRepository.findById(equipment_model_id)
                    .orElseThrow(() -> new Exception("Not found [equipment_model_id] with id = " + equipment_model_id));

            equipmentModelRepository.deleteById(equipment_model_id);

            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
