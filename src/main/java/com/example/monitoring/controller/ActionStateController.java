package com.example.monitoring.controller;

import com.example.monitoring.model.ActionState;
import com.example.monitoring.repository.ActionStateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/api")
public class ActionStateController {

    @Autowired
    private ActionStateRepository actionStateRepository;

    @GetMapping("/action-state")
    public ResponseEntity<List<ActionState>> getActionStateList(@RequestParam(required = false) String action_state_name) {
        List<ActionState> actionStates = new ArrayList<>();

        if (action_state_name == null) {
            actionStates.addAll(actionStateRepository.findAll());
        } else {
            actionStates.addAll(actionStateRepository.findByAction_state_name(action_state_name));
        }

        if (actionStates.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(actionStates, HttpStatus.OK);
    }

    @GetMapping("/action-state/{action_state_id}")
    public ResponseEntity<ActionState> getActionStateById(@PathVariable("action_state_id") Integer action_state_id) throws Exception {
        ActionState actionState = actionStateRepository.findById(action_state_id)
                .orElseThrow(() -> new Exception("Not found [action_state] with id = " + action_state_id));
        return new ResponseEntity<>(actionState, HttpStatus.OK);
    }

    @PostMapping("/action-state")
    public ResponseEntity<ActionState> createActionState(@RequestBody ActionState actionState) {
        ActionState entity = actionStateRepository
                .save(new ActionState(actionState.getAction_state_name()));
        return new ResponseEntity<>(entity, HttpStatus.CREATED);
    }

    @PutMapping("/action-state/{action_state_id}")
    public ResponseEntity<ActionState> updateActionState(@PathVariable("action_state_id") Integer action_state_id,
                                                         @RequestBody ActionState actionState) throws Exception {
        ActionState entity = actionStateRepository.findById(action_state_id)
                .orElseThrow(() -> new Exception("Not found [action_state] with id = " + action_state_id));

        entity.setAction_state_name(actionState.getAction_state_name());

        return new ResponseEntity<>(actionStateRepository.save(entity), HttpStatus.OK);
    }

    @DeleteMapping("/action-state/{action_state_id}")
    public ResponseEntity<HttpStatus> deleteRepairActionState(@PathVariable("action_state_id") Integer action_state_id) {
        actionStateRepository.deleteById(action_state_id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
