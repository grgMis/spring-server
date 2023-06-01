package com.example.monitoring.controller;

import com.example.monitoring.model.*;
import com.example.monitoring.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/api")
public class ActionCompositionController {

    @Autowired
    private ActionCompositionRepository actionCompositionRepository;

    @Autowired
    private ActionRepository actionRepository;

    @Autowired
    private EquipmentRepository equipmentRepository;

    @Autowired
    private ActionCompositionStateRepository actionCompositionStateRepository;

    @Autowired
    private WellRepository wellRepository;

    @Autowired
    private EquipmentStateRepository equipmentStateRepository;

    @Autowired
    private EquipmentCategoryRepository equipmentCategoryRepository;

    @GetMapping("/action-composition")
    public ResponseEntity<List<ActionComposition>> getListActionComposition(@RequestParam(required = false) Integer action_id,
                                                                            @RequestParam(required = false) Integer well_id,
                                                                            @RequestParam(required = false) Integer equipment_state_id,
                                                                            @RequestParam(required = false) Integer equipment_category_id,
                                                                            @RequestParam(required = false) Integer equipment_id,
                                                                            @RequestParam(required = false) Integer action_composition_state_id) {

        try {
            List<ActionComposition> actionCompositions = new ArrayList<>();

            Action action;
            Well well;
            EquipmentState equipmentState;
            EquipmentCategory equipmentCategory;
            Equipment equipment;
            ActionCompositionState actionCompositionState;

            if (action_id == null &&
                equipment_id == null &&
                action_composition_state_id == null &&
                well_id == null &&
                equipment_state_id == null &&
                equipment_category_id == null) {
                actionCompositions.addAll(actionCompositionRepository.findAll());
            }

            if (well_id != null && equipment_state_id != null) {
                well = wellRepository.findById(well_id)
                        .orElseThrow(() -> new Exception("Not found [well] with id = " + well_id));

                equipmentState = equipmentStateRepository.findById(equipment_state_id)
                        .orElseThrow(() -> new Exception("Not found [equipment_state] with id = " + equipment_state_id));

                actionCompositions.addAll(actionCompositionRepository.findByAction_WellAndEquipment_EquipmentState(well, equipmentState));
            }

            if (well_id != null && equipment_category_id != null) {
                well = wellRepository.findById(well_id)
                        .orElseThrow(() -> new Exception("Not found [well] with id = " + well_id));

                equipmentCategory = equipmentCategoryRepository.findById(equipment_category_id)
                        .orElseThrow(() -> new Exception("Not found [equipment_category] with id = " + equipment_category_id));

                actionCompositions.addAll(actionCompositionRepository.findByAction_WellAndEquipment_EquipmentModel_EquipmentClass_EquipmentCategory(well, equipmentCategory));
            }

            if (action_id != null) {
                action = actionRepository.findById(action_id)
                        .orElseThrow(() -> new Exception("Not found [action] with id = " + action_id));
                actionCompositions.addAll(actionCompositionRepository.findByAction(action));
            }

            if (equipment_id != null) {
                equipment = equipmentRepository.findById(equipment_id)
                        .orElseThrow(() -> new Exception("Not found [equipment] with id = " + equipment_id));
                actionCompositions.addAll(actionCompositionRepository.findByEquipment(equipment));
            }

            if (action_composition_state_id != null) {
                actionCompositionState = actionCompositionStateRepository.findById(action_composition_state_id)
                        .orElseThrow(() -> new Exception("Not found [action_composition_state] with id = " + action_composition_state_id));
                actionCompositions.addAll(actionCompositionRepository.findByActionCompositionState(actionCompositionState));
            }

            if (actionCompositions.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(actionCompositions, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/action-composition/{action_composition_id}")
    public ResponseEntity<ActionComposition> getActionCompositionById(@PathVariable("action_composition_id") Integer action_composition_id) {

        try {
            ActionComposition actionComposition = actionCompositionRepository.findById(action_composition_id)
                    .orElseThrow(() -> new Exception("Not found [action_composition] with id = " + action_composition_id));

            return new ResponseEntity<>(actionComposition, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/action-composition")
    public ResponseEntity<ActionComposition> createActionComposition(@RequestParam Integer action_id,
                                                                     @RequestParam Integer equipment_id,
                                                                     @RequestParam Integer action_composition_state_id,
                                                                     @RequestBody(required = false) ActionComposition actionComposition) {

        try {
            ActionComposition entity = new ActionComposition();

            Action action = actionRepository.findById(action_id)
                    .orElseThrow(() -> new Exception("Not found [action] with id = " + action_id));

            Equipment equipment = equipmentRepository.findById(equipment_id)
                    .orElseThrow(() -> new Exception("Not found [equipment] with id = " + equipment_id));

            ActionCompositionState actionCompositionState = actionCompositionStateRepository.findById(action_composition_state_id)
                    .orElseThrow(() -> new Exception("Not found [action_composition_state] with id = " + action_composition_state_id));


            entity.setAction(action);
            entity.setEquipment(equipment);
            entity.setActionCompositionState(actionCompositionState);

            if (actionComposition != null) {
                entity.setDate_complete(actionComposition.getDate_complete());
                entity.setAction_composition_note(actionComposition.getAction_composition_note());
            }

            actionCompositionRepository.save(entity);

            return new ResponseEntity<>(entity, HttpStatus.CREATED);
        } catch (Exception ex) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/action-composition/{action_composition_id}")
    public ResponseEntity<ActionComposition> updateActionComposition(@PathVariable("action_composition_id") Integer action_composition_id,
                                                                     @RequestParam(required = false) Integer action_id,
                                                                     @RequestParam(required = false) Integer equipment_id,
                                                                     @RequestParam(required = false) Integer action_composition_state_id,
                                                                     @RequestBody(required = false) ActionComposition actionComposition) {

        try {
            Action action;
            Equipment equipment;
            ActionCompositionState actionCompositionState;

            ActionComposition entity = actionCompositionRepository.findById(action_composition_id)
                    .orElseThrow(() -> new Exception("Not found [action_composition] with id = " + action_composition_id));

            if (action_id != null) {
                action = actionRepository.findById(action_id)
                        .orElseThrow(() -> new Exception("Not found [action] with id = " + action_id));
                entity.setAction(action);
            }

            if (equipment_id != null) {
                equipment = equipmentRepository.findById(equipment_id)
                        .orElseThrow(() -> new Exception("Not found [equipment] with id = " + equipment_id));
                entity.setEquipment(equipment);
            }

            if (action_composition_state_id != null) {
                actionCompositionState = actionCompositionStateRepository.findById(action_composition_state_id)
                        .orElseThrow(() -> new Exception("Not found [action_composition_state] with id = " + action_composition_state_id));
                entity.setActionCompositionState(actionCompositionState);
            }

            if (actionComposition != null) {
                entity.setDate_complete(actionComposition.getDate_complete());
                entity.setAction_composition_note(actionComposition.getAction_composition_note());
            }

            actionCompositionRepository.save(entity);

            return new ResponseEntity<>(entity, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/action-composition/{action_composition_id}")
    public ResponseEntity<ActionComposition> deleteActionComposition(@PathVariable("action_composition_id") Integer action_composition_id) {

        try {
            actionCompositionRepository.findById(action_composition_id)
                    .orElseThrow(() -> new Exception("Not found [action_composition] with id = " + action_composition_id));

            actionCompositionRepository.deleteById(action_composition_id);

            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
