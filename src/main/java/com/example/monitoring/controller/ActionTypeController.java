package com.example.monitoring.controller;

import com.example.monitoring.model.ActionGroup;
import com.example.monitoring.model.ActionType;
import com.example.monitoring.repository.ActionGroupRepository;
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

    @Autowired
    private ActionGroupRepository actionGroupRepository;

    @GetMapping("/action-type")
    public ResponseEntity<List<ActionType>> getActionTypeList(@RequestParam(required = false) String action_type_name,
                                                              @RequestParam(required = false) Integer action_group_id) throws Exception {
        List<ActionType> actionTypes = new ArrayList<>();

        ActionGroup actionGroup;

        if (action_type_name != null) {
            actionTypes.addAll(actionTypeRepository.findByAction_type_name(action_type_name));
        }

        if (action_group_id != null) {
            actionGroup = actionGroupRepository.findById(action_group_id)
                    .orElseThrow(() -> new Exception("Not found [action_group] with id = " + action_group_id));
            actionTypes.addAll(actionTypeRepository.findByActionGroup(actionGroup));
            return new ResponseEntity<>(actionTypes, HttpStatus.OK);
        }

        if (action_type_name == null && action_group_id == null) {
            actionTypes.addAll(actionTypeRepository.findAll());
            return new ResponseEntity<>(actionTypes, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/action-type/{action_type_id}")
    public ResponseEntity<ActionType> getActionTypeById(@PathVariable("action_type_id") Integer action_type_id) throws Exception {
        ActionType actionType = actionTypeRepository.findById(action_type_id)
                .orElseThrow(() -> new Exception("Not found [action_type] with id = " + action_type_id));
        return new ResponseEntity<>(actionType, HttpStatus.OK);
    }

    @PostMapping("/action-type")
    public ResponseEntity<ActionType> createActionType(@RequestParam Integer action_group_id,
                                                       @RequestBody ActionType actionType) throws Exception {
        ActionType entity = actionGroupRepository.findById(action_group_id).map(actionGroup -> {
            actionType.setActionGroup(actionGroup);
            return actionTypeRepository.save(actionType);
        }).orElseThrow(() -> new Exception("Not found [action_group] with id = " + action_group_id));

        return new ResponseEntity<>(entity, HttpStatus.CREATED);
    }

    @PutMapping("/action-type/{action_type_id}")
    public ResponseEntity<ActionType> updateActionType(@PathVariable("action_type_id") Integer action_type_id,
                                                       @RequestParam(required = false) Integer action_group_id,
                                                       @RequestBody ActionType actionType) throws Exception {
        ActionType entity;

        entity = actionTypeRepository.findById(action_type_id)
                .orElseThrow(() -> new Exception("Not found [action_type] with id = " + action_type_id));

        if (action_group_id != null) {
            ActionGroup actionGroup = actionGroupRepository.findById(action_group_id)
                    .orElseThrow(() -> new Exception("Not found [action_group] with id = " + action_group_id));

            entity.setActionGroup(actionGroup);
        }
        entity.setAction_type_name(actionType.getAction_type_name());
        entity.setAction_type_sysname(actionType.getAction_type_sysname());

        return new ResponseEntity<>(actionTypeRepository.save(entity), HttpStatus.OK);
    }

    @DeleteMapping("/action-type/{action_type_id}")
    public ResponseEntity<ActionType> deleteActionType(@PathVariable("action_type_id") Integer action_type_id) {
        actionTypeRepository.deleteById(action_type_id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
