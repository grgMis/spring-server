package com.example.monitoring.controller;

import com.example.monitoring.model.ActionGroup;
import com.example.monitoring.repository.ActionGroupRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/api")
public class ActionGroupController {

    private ActionGroupRepository actionGroupRepository;

    @GetMapping("/action-group")
    public ResponseEntity<List<ActionGroup>> getActionGroupList(@RequestParam(required = false) String action_group_name) {

        List<ActionGroup> actionGroups = new ArrayList<>();

        if (action_group_name == null) {
            actionGroups.addAll(actionGroupRepository.findAll());
        } else {
            actionGroups.addAll(actionGroupRepository.findByAction_group_name(action_group_name));
        }

        if (actionGroups.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(actionGroups, HttpStatus.OK);
    }

    @GetMapping("/action-group/{action_group_id}")
    public ResponseEntity<ActionGroup> getActionGroupById(@PathVariable("action_group_id") Integer action_group_id) throws Exception {
        ActionGroup actionGroup = actionGroupRepository.findById(action_group_id)
                .orElseThrow(() -> new Exception("Not found [action_group] with id = " + action_group_id));
        return new ResponseEntity<>(actionGroup, HttpStatus.OK);
    }

    @PostMapping("/action-group")
    public ResponseEntity<ActionGroup> createActionGroup(@RequestBody ActionGroup actionGroup) {
        ActionGroup entity = actionGroupRepository
                .save(new ActionGroup(actionGroup.getAction_group_name(), actionGroup.getAction_group_sysname()));
        return new ResponseEntity<>(entity, HttpStatus.CREATED);
    }

    @PutMapping("/action-group/{action_group_id}")
    public ResponseEntity<ActionGroup> updateActionGroup(@PathVariable("action_group_id") Integer action_group_id,
                                                         @RequestBody ActionGroup actionGroup) throws Exception {
        ActionGroup entity = actionGroupRepository.findById(action_group_id)
                .orElseThrow(() -> new Exception("Not found [action_group] with id = " + action_group_id));

        entity.setAction_group_name(actionGroup.getAction_group_name());
        entity.setAction_group_sysname(actionGroup.getAction_group_sysname());

        return new ResponseEntity<>(actionGroupRepository.save(entity), HttpStatus.OK);
    }

    @DeleteMapping("/action-group/{action_group_id}")
    public ResponseEntity<HttpStatus> deleteActionGroup(@PathVariable("action_group_id") Integer action_group_id) {
        actionGroupRepository.deleteById(action_group_id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
