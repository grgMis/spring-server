package com.example.monitoring.controller;

import com.example.monitoring.model.*;
import com.example.monitoring.repository.ActionRepository;
import com.example.monitoring.repository.ActionStateRepository;
import com.example.monitoring.repository.ActionTypeRepository;
import com.example.monitoring.repository.WellEquipmentRepository;
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
public class ActionController {

    @Autowired
    private ActionRepository actionRepository;

    @Autowired
    private WellEquipmentRepository wellEquipmentRepository;

    @Autowired
    private ActionTypeRepository actionTypeRepository;

    @Autowired
    private ActionStateRepository actionStateRepository;

    @GetMapping("/action")
    public ResponseEntity<List<Action>> getActionList(@RequestParam(required = false) Integer well_equipment_id,
                                                      @RequestParam(required = false) Integer action_type_id,
                                                      @RequestParam(required = false) Integer action_state_id) throws Exception {

        List<Action> actions = new ArrayList<>();

        WellEquipment wellEquipment;
        ActionType actionType;
        ActionState actionState;

        if (well_equipment_id != null) {
            wellEquipment = wellEquipmentRepository.findById(well_equipment_id)
                    .orElseThrow(() -> new Exception("Not found [well_equipment] with id = " + well_equipment_id));
            actions.addAll(actionRepository.findByWellEquipment(wellEquipment));
            return new ResponseEntity<>(actions, HttpStatus.OK);
        }

        if (action_type_id != null) {
            actionType = actionTypeRepository.findById(action_type_id)
                    .orElseThrow(() -> new Exception("Not found [action_type] with id = " + action_type_id));
            actions.addAll(actionRepository.findByActionType(actionType));
            return new ResponseEntity<>(actions, HttpStatus.OK);
        }

        if (action_state_id != null) {
            actionState = actionStateRepository.findById(action_state_id)
                    .orElseThrow(() -> new Exception("Not found [action_state] with id = " + action_state_id));
            actions.addAll(actionRepository.findByActionState(actionState));
            return new ResponseEntity<>(actions, HttpStatus.OK);
        }

        if (well_equipment_id == null && action_type_id == null && action_state_id == null) {
            actions.addAll(actionRepository.findAll());
        }

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/action/{action_id}")
    public ResponseEntity<Action> getActionById(@PathVariable("action_id") Integer action_id) throws Exception {
        Action action = actionRepository.findById(action_id)
                .orElseThrow(() -> new Exception("Not found [action] with id = " + action_id));
        return new ResponseEntity<>(action, HttpStatus.OK);
    }

    @PostMapping("/action")
    public ResponseEntity<Action> createAction(@RequestParam Integer well_equipment_id,
                                               @RequestParam Integer action_type_id,
                                               @RequestParam Integer action_state_id,
                                               @RequestBody(required = false) Action action) throws Exception {

        Date date = new Date();

        Action entity = new Action();

        WellEquipment wellEquipment = wellEquipmentRepository.findById(well_equipment_id)
                .orElseThrow(() -> new Exception("Not found [well_equipment] with id = " + well_equipment_id));

        ActionType actionType = actionTypeRepository.findById(action_type_id)
                .orElseThrow(() -> new Exception("Not found [action_type] with id = " + action_type_id));

        ActionState actionState = actionStateRepository.findById(action_state_id)
                .orElseThrow(() -> new Exception("Not found [action_state] with id = " + action_state_id));

        entity.setWellEquipment(wellEquipment);
        entity.setActionType(actionType);
        entity.setActionState(actionState);
        entity.setDate_begin(date);

        if (action != null) {
            entity.setDate_end(action.getDate_end());
        }

        actionRepository.save(entity);

        return new ResponseEntity<>(entity, HttpStatus.CREATED);
    }

    @PutMapping("/action/{action_id}")
    public ResponseEntity<Action> updateAction(@PathVariable("action_id") Integer action_id,
                                               @RequestParam(required = false) Integer well_equipment_id,
                                               @RequestParam(required = false) Integer action_type_id,
                                               @RequestParam(required = false) Integer action_state_id,
                                               @RequestBody(required = false) Action action) throws Exception {
        Date date = new Date();

        WellEquipment wellEquipment;
        ActionType actionType;
        ActionState actionState;

        Action entity = actionRepository.findById(action_id)
                .orElseThrow(() -> new Exception("Not found [action] with id = " + action_id));

        if (well_equipment_id != null) {
            wellEquipment = wellEquipmentRepository.findById(well_equipment_id)
                    .orElseThrow(() -> new Exception("Not found [well_equipment] with id = " + well_equipment_id));
            entity.setWellEquipment(wellEquipment);
        }

        if (action_type_id != null) {
            actionType = actionTypeRepository.findById(action_type_id)
                    .orElseThrow(() -> new Exception("Not found [action_type] with id = " + action_type_id));
            entity.setActionType(actionType);
        }

        if (action_state_id != null) {
            actionState = actionStateRepository.findById(action_state_id)
                    .orElseThrow(() -> new Exception("Not found [action_state] with id = " + action_state_id));
            entity.setActionState(actionState);
        }

        if (action != null) {
            entity.setDate_end(date);
        }

        actionRepository.save(entity);

        return new ResponseEntity<>(entity, HttpStatus.OK);
    }

    @DeleteMapping("/action/{action_id}")
    public ResponseEntity<Action> deleteAction(@PathVariable("action_id") Integer action_id) {

        actionRepository.deleteById(action_id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
