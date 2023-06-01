package com.example.monitoring.controller;

import com.example.monitoring.model.ActionCompositionState;
import com.example.monitoring.repository.ActionCompositionStateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/api")
public class ActionCompositionStateController {

    @Autowired
    private ActionCompositionStateRepository actionCompositionStateRepository;

    @GetMapping("/action-composition-state")
    public ResponseEntity<List<ActionCompositionState>> getListActionCompositionState(@RequestParam(required = false) String action_composition_name) {

        try {
            List<ActionCompositionState> actionCompositionsStates = new ArrayList<>();

            if (action_composition_name == null) {
                actionCompositionsStates.addAll(actionCompositionStateRepository.findAll());
            } else {
                actionCompositionsStates.addAll(actionCompositionStateRepository.findByAction_composition_state_name(action_composition_name));
            }

            if (actionCompositionsStates.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(actionCompositionsStates, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/action-composition-state/{action-composition-state-id}")
    public ResponseEntity<ActionCompositionState> getActionCompositionStateById(@PathVariable("action_composition_state_id") Integer action_composition_state_id) {

        try {
            ActionCompositionState actionCompositionState = actionCompositionStateRepository.findById(action_composition_state_id)
                    .orElseThrow(() -> new Exception("Not found [action_composition_state] with id = " + action_composition_state_id));

            return new ResponseEntity<>(actionCompositionState, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/action-composition-state")
    public ResponseEntity<ActionCompositionState> createActionCompositionState(@RequestBody ActionCompositionState actionCompositionState) {

        try {
            ActionCompositionState entity = actionCompositionStateRepository
                    .save(new ActionCompositionState(actionCompositionState.getAction_composition_state_name()));

            return new ResponseEntity<>(entity, HttpStatus.CREATED);
        } catch (Exception ex) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/action-composition-state/{action-composition-state-id}")
    public ResponseEntity<ActionCompositionState> updateActionCompositionState(@PathVariable("action_composition_state_id") Integer action_composition_state_id,
                                                                               @RequestBody ActionCompositionState actionCompositionState) {

        try {
            ActionCompositionState entity = actionCompositionStateRepository.findById(action_composition_state_id)
                    .orElseThrow(() -> new Exception("Not found [action_composition_state] with id = " + action_composition_state_id));

            entity.setAction_composition_state_name(actionCompositionState.getAction_composition_state_name());

            actionCompositionStateRepository.save(entity);

            return new ResponseEntity<>(entity, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/action-composition-state/{action-composition-state-id}")
    public ResponseEntity<HttpStatus> deleteActionCompositionState(@PathVariable("action_composition_state_id") Integer action_composition_state_id) {

        try {
            actionCompositionStateRepository.findById(action_composition_state_id)
                    .orElseThrow(() -> new Exception("Not found [action_composition_state] with id = " + action_composition_state_id));

            actionCompositionStateRepository.deleteById(action_composition_state_id);

            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
