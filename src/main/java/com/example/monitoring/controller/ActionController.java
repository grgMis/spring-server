package com.example.monitoring.controller;

import com.example.monitoring.model.*;
import com.example.monitoring.repository.*;
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
    private WellRepository wellRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ActionTypeRepository actionTypeRepository;

    @Autowired
    private ActionStateRepository actionStateRepository;

    @GetMapping("/action")
    public ResponseEntity<List<Action>> getActionList(@RequestParam(required = false) Integer well_id,
                                                      @RequestParam(required = false) Integer user_id,
                                                      @RequestParam(required = false) Integer action_type_id,
                                                      @RequestParam(required = false) Integer action_state_id) {

        try {
            List<Action> actions = new ArrayList<>();

            Well well;
            User user;
            ActionType actionType;
            ActionState actionState;

            if (well_id == null && user_id == null && action_type_id == null && action_state_id == null) {
                actions.addAll(actionRepository.findAll());
            }

            if (well_id != null) {
                well = wellRepository.findById(well_id)
                        .orElseThrow(() -> new Exception("Not found [well] with id = " + well_id));
                actions.addAll(actionRepository.findByWell(well));
            }

            if (user_id != null) {
                user = userRepository.findById(user_id)
                        .orElseThrow(() -> new Exception("Not found [user] with id = " + user_id));
                actions.addAll(actionRepository.findByUser(user));
            }

            if (action_type_id != null) {
                actionType = actionTypeRepository.findById(action_type_id)
                        .orElseThrow(() -> new Exception("Not found [action_type] with id = " + action_type_id));
                actions.addAll(actionRepository.findByActionType(actionType));
            }

            if (action_state_id != null) {
                actionState = actionStateRepository.findById(action_state_id)
                        .orElseThrow(() -> new Exception("Not found [action_state] with id = " + action_state_id));
                actions.addAll(actionRepository.findByActionState(actionState));
                return new ResponseEntity<>(actions, HttpStatus.OK);
            }

            if (actions.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(actions, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/action/{action_id}")
    public ResponseEntity<Action> getActionById(@PathVariable("action_id") Integer action_id) {

        try {
            Action action = actionRepository.findById(action_id)
                    .orElseThrow(() -> new Exception("Not found [action] with id = " + action_id));

            return new ResponseEntity<>(action, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/action")
    public ResponseEntity<Action> createAction(@RequestParam Integer well_id,
                                               @RequestParam Integer user_id,
                                               @RequestParam Integer action_type_id,
                                               @RequestParam Integer action_state_id,
                                               @RequestBody(required = false) Action action) {

        try {
            Date date = new Date();
            Action entity = new Action();

            Well well = wellRepository.findById(well_id)
                    .orElseThrow(() -> new Exception("Not found [well] with id = " + well_id));

            User user = userRepository.findById(user_id)
                    .orElseThrow(() -> new Exception("Not found [user] with id = " + user_id));

            ActionType actionType = actionTypeRepository.findById(action_type_id)
                    .orElseThrow(() -> new Exception("Not found [action_type] with id = " + action_type_id));

            ActionState actionState = actionStateRepository.findById(action_state_id)
                    .orElseThrow(() -> new Exception("Not found [action_state] with id = " + action_state_id));

            entity.setWell(well);
            entity.setUser(user);
            entity.setActionType(actionType);
            entity.setActionState(actionState);

            if (action != null) {
                entity.setDate_entry(date);
                entity.setDate_begin(action.getDate_begin());
                entity.setDate_end(action.getDate_end());
                entity.setAction_note(action.getAction_note());
            }

            actionRepository.save(entity);

            return new ResponseEntity<>(entity, HttpStatus.CREATED);
        } catch (Exception ex) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/action/{action_id}")
    public ResponseEntity<Action> updateAction(@PathVariable("action_id") Integer action_id,
                                               @RequestParam(required = false) Integer well_id,
                                               @RequestParam(required = false) Integer user_id,
                                               @RequestParam(required = false) Integer action_type_id,
                                               @RequestParam(required = false) Integer action_state_id,
                                               @RequestBody(required = false) Action action) {

        try {
            Well well;
            User user;
            ActionType actionType;
            ActionState actionState;

            Action entity = actionRepository.findById(action_id)
                    .orElseThrow(() -> new Exception("Not found [action] with id = " + action_id));

            if (well_id != null) {
                well = wellRepository.findById(well_id)
                        .orElseThrow(() -> new Exception("Not found [well] with id = " + well_id));
                entity.setWell(well);
            }

            if (user_id != null) {
                user = userRepository.findById(user_id)
                        .orElseThrow(() -> new Exception("Not found [user] with id = " + user_id));
                entity.setUser(user);
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
                entity.setDate_begin(action.getDate_begin());
                entity.setDate_end(action.getDate_end());
                entity.setAction_note(action.getAction_note());
            }

            actionRepository.save(entity);

            return new ResponseEntity<>(entity, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/action/{action_id}")
    public ResponseEntity<Action> deleteAction(@PathVariable("action_id") Integer action_id) {

        try {
            actionRepository.findById(action_id)
                    .orElseThrow(() -> new Exception("Not found [action] with id = " + action_id));

            actionRepository.deleteById(action_id);

            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
