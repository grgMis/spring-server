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
    private UserRepository userRepository;

    @Autowired
    private ActionCompositionStateRepository actionCompositionStateRepository;

    @GetMapping("/action-composition")
    public ResponseEntity<List<ActionComposition>> getListActionComposition(@RequestParam(required = false) Integer action_id,
                                                                            @RequestParam(required = false) Integer equipment_id,
                                                                            @RequestParam(required = false) Integer user_id,
                                                                            @RequestParam(required = false) Integer action_composition_state_id) throws Exception {
        List<ActionComposition> actionCompositions = new ArrayList<>();

        Action action;
        Equipment equipment;
        User user;
        ActionCompositionState actionCompositionState;

        if (action_id != null) {
            action = actionRepository.findById(action_id)
                    .orElseThrow(() -> new Exception("Not found [action] with id = " + action_id));
            actionCompositions.addAll(actionCompositionRepository.findByAction(action));
            return new ResponseEntity<>(actionCompositions, HttpStatus.OK);
        }

        if (equipment_id != null) {
            equipment = equipmentRepository.findById(equipment_id)
                    .orElseThrow(() -> new Exception("Not found [equipment] with id = " + equipment_id));
            actionCompositions.addAll(actionCompositionRepository.findByEquipment(equipment));
            return new ResponseEntity<>(actionCompositions, HttpStatus.OK);
        }

        if (user_id != null) {
            user = userRepository.findById(user_id)
                    .orElseThrow(() -> new Exception("Not found [user] with id = " + user_id));
            actionCompositions.addAll(actionCompositionRepository.findByUser(user));
            return new ResponseEntity<>(actionCompositions, HttpStatus.OK);
        }

        if (action_composition_state_id != null) {
            actionCompositionState = actionCompositionStateRepository.findById(action_composition_state_id)
                    .orElseThrow(() -> new Exception("Not found [action_composition_state] with id = " + action_composition_state_id));
            actionCompositions.addAll(actionCompositionRepository.findByActionCompositionState(actionCompositionState));
            return new ResponseEntity<>(actionCompositions, HttpStatus.OK);
        }

        if (actionCompositions.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        actionCompositions.addAll(actionCompositionRepository.findAll());
        return new ResponseEntity<>(actionCompositions, HttpStatus.OK);
    }

    @GetMapping("/action-composition/{action_composition_id}")
    public ResponseEntity<ActionComposition> getActionCompositionById(@PathVariable("action_composition_id") Integer action_composition_id) throws Exception {
        ActionComposition actionComposition = actionCompositionRepository.findById(action_composition_id)
                .orElseThrow(() -> new Exception("Not found [action_composition] with id = " + action_composition_id));
        return new ResponseEntity<>(actionComposition, HttpStatus.OK);
    }

    @PostMapping("/action-composition")
    public ResponseEntity<ActionComposition> createActionComposition(@RequestParam Integer action_id,
                                                                     @RequestParam Integer equipment_id,
                                                                     @RequestParam Integer user_id,
                                                                     @RequestParam Integer action_composition_state_id,
                                                                     @RequestBody(required = false) ActionComposition actionComposition) throws Exception {

        ActionComposition entity = new ActionComposition();

        Action action = actionRepository.findById(action_id)
                .orElseThrow(() -> new Exception("Not found [action] with id = " + action_id));

        Equipment equipment = equipmentRepository.findById(equipment_id)
                .orElseThrow(() -> new Exception("Not found [equipment] with id = " + equipment_id));

        User user = userRepository.findById(user_id)
                .orElseThrow(() -> new Exception("Not found [user_id] with id = " + user_id));

        ActionCompositionState actionCompositionState = actionCompositionStateRepository.findById(action_composition_state_id)
                .orElseThrow(() -> new Exception("Not found [action_composition_state] with id = " + action_composition_state_id));

        entity.setAction(action);
        entity.setEquipment(equipment);
        entity.setUser(user);
        entity.setActionCompositionState(actionCompositionState);

        if (actionComposition != null) {
            entity.setDate_complete(actionComposition.getDate_complete());
            entity.setAction_composition_note(actionComposition.getAction_composition_note());
        }

        actionCompositionRepository.save(entity);

        return new ResponseEntity<>(entity, HttpStatus.CREATED);
    }

    @PutMapping("/action-composition/{action_composition_id}")
    public ResponseEntity<ActionComposition> updateActionComposition(@PathVariable("action_composition_id") Integer action_composition_id,
                                                                     @RequestParam(required = false) Integer action_id,
                                                                     @RequestParam(required = false) Integer equipment_id,
                                                                     @RequestParam(required = false) Integer user_id,
                                                                     @RequestParam(required = false) Integer action_composition_state_id,
                                                                     @RequestBody(required = false) ActionComposition actionComposition) throws Exception {

        Action action;
        Equipment equipment;
        User user;
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

        if (user_id != null) {
            user = userRepository.findById(user_id)
                    .orElseThrow(() -> new Exception("Not found [user_id] with id = " + user_id));
            entity.setUser(user);
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
    }

    @DeleteMapping("/action-composition/{action_composition_id}")
    public ResponseEntity<ActionComposition> deleteActionComposition(@PathVariable("action_composition_id") Integer action_composition_id) {

        actionCompositionRepository.deleteById(action_composition_id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
