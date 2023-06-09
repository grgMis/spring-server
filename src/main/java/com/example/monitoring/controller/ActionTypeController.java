package com.example.monitoring.controller;

import com.example.monitoring.model.ActionType;
import com.example.monitoring.repository.ActionTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/api")
public class ActionTypeController {

    @Autowired
    private ActionTypeRepository actionTypeRepository;

    @GetMapping("/action-type")
    public ResponseEntity<List<ActionType>> getActionTypeList(@RequestParam(required = false) String action_type_name) {

        try {
            List<ActionType> actionTypes = new ArrayList<>();

            if (action_type_name == null) {
                actionTypes.addAll(actionTypeRepository.findAll());
            } else {
                actionTypes.addAll(actionTypeRepository.findByAction_type_name(action_type_name));
            }

            if (actionTypes.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(actionTypes, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/action-type/{action_type_id}")
    public ResponseEntity<ActionType> getActionTypeById(@PathVariable("action_type_id") Integer action_type_id) {

        try {
            ActionType actionType = actionTypeRepository.findById(action_type_id)
                    .orElseThrow(() -> new Exception("Not found [action_type] with id = " + action_type_id));

            return new ResponseEntity<>(actionType, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/action-type")
    public ResponseEntity<ActionType> createActionType(@RequestBody ActionType actionType) {

        try {
            ActionType entity = actionTypeRepository
                    .save(new ActionType(actionType.getAction_type_name()));

            return new ResponseEntity<>(entity, HttpStatus.CREATED);
        } catch (Exception ex) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/action-type/{action_type_id}")
    public ResponseEntity<ActionType> updateActionType(@PathVariable("action_type_id") Integer action_type_id,
                                                       @RequestBody ActionType actionType) {

        try {
            ActionType entity = actionTypeRepository.findById(action_type_id)
                    .orElseThrow(() -> new Exception("Not found [action_type] with id = " + action_type_id));

            entity.setAction_type_name(actionType.getAction_type_name());

            actionTypeRepository.save(entity);

            return new ResponseEntity<>(entity, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/action-type/{action_type_id}")
    public ResponseEntity<HttpStatus> deleteActionType(@PathVariable("action_type_id") Integer action_type_id) {

        try {
            actionTypeRepository.findById(action_type_id)
                    .orElseThrow(() -> new Exception("Not found [action_type] with id = " + action_type_id));

            actionTypeRepository.deleteById(action_type_id);

            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
