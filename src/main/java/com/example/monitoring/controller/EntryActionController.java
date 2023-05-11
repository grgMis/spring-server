package com.example.monitoring.controller;

import com.example.monitoring.model.*;
import com.example.monitoring.repository.EntryActionRepository;
import com.example.monitoring.repository.EntryActionStateRepository;
import com.example.monitoring.repository.EntryActionTypeRepository;
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
public class EntryActionController {

    @Autowired
    private EntryActionRepository entryActionRepository;

    @Autowired
    private WellEquipmentRepository wellEquipmentRepository;

    @Autowired
    private EntryActionTypeRepository entryActionTypeRepository;

    @Autowired
    private EntryActionStateRepository entryActionStateRepository;

    @GetMapping("/entry-action")
    public ResponseEntity<List<EntryAction>> getListEntryAction(@RequestParam(required = false) Integer well_equipment_id,
                                                                @RequestParam(required = false) Integer entry_action_type_id,
                                                                @RequestParam(required = false) Integer entry_action_state_id) throws Exception {

        List<EntryAction> entryActions = new ArrayList<>();

        WellEquipment wellEquipment;
        EntryActionType entryActionType;
        EntryActionState entryActionState;

        if (well_equipment_id != null) {
            wellEquipment = wellEquipmentRepository.findById(well_equipment_id)
                    .orElseThrow(() -> new Exception("Not found [well_equipment] with id = " + well_equipment_id));
            entryActions.addAll(entryActionRepository.findByWellEquipment(wellEquipment));
            return new ResponseEntity<>(entryActions, HttpStatus.OK);
        }

        if (entry_action_type_id != null) {
            entryActionType = entryActionTypeRepository.findById(entry_action_type_id)
                    .orElseThrow(() -> new Exception("Not found [entry_action_type] with id = " + entry_action_type_id));
            entryActions.addAll(entryActionRepository.findByEntryActionType(entryActionType));
            return new ResponseEntity<>(entryActions, HttpStatus.OK);
        }

        if (entry_action_state_id != null) {
            entryActionState = entryActionStateRepository.findById(entry_action_state_id)
                    .orElseThrow(() -> new Exception("Not found [entry_action_state] with id = " + entry_action_state_id));
            entryActions.addAll(entryActionRepository.findByEntryActionState(entryActionState));
            return new ResponseEntity<>(entryActions, HttpStatus.OK);
        }

        if (well_equipment_id == null && entry_action_type_id == null && entry_action_state_id == null) {
            entryActions.addAll(entryActionRepository.findAll());
            return new ResponseEntity<>(entryActions, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/entry-action/{entry_action_id}")
    public ResponseEntity<EntryAction> getEntryActionById(@PathVariable("entry_action_id") Integer entry_action_id) throws Exception {
        EntryAction entryAction = entryActionRepository.findById(entry_action_id)
                .orElseThrow(() -> new Exception("Not found [entry_action] with id = " + entry_action_id));
        return new ResponseEntity<>(entryAction, HttpStatus.OK);
    }

    @PostMapping("/entry-action")
    public ResponseEntity<EntryAction> createEntryAction(@RequestParam Integer well_equipment_id,
                                                         @RequestParam Integer entry_action_type_id,
                                                         @RequestParam Integer entry_action_state_id,
                                                         @RequestBody(required = false) EntryAction entryAction) throws Exception {

        Date date = new Date();

        EntryAction entity = new EntryAction();

        WellEquipment wellEquipment = wellEquipmentRepository.findById(well_equipment_id)
                .orElseThrow(() -> new Exception("Not found [well_equipment] with id = " + well_equipment_id));

        EntryActionType entryActionType = entryActionTypeRepository.findById(entry_action_type_id)
                .orElseThrow(() -> new Exception("Not found [entry_action_type] with id = " + entry_action_type_id));

        EntryActionState entryActionState = entryActionStateRepository.findById(entry_action_state_id)
                .orElseThrow(() -> new Exception("Not found [entry_action_state] with id = " + entry_action_state_id));

        entity.setWellEquipment(wellEquipment);
        entity.setEntryActionType(entryActionType);
        entity.setEntryActionState(entryActionState);
        entity.setDate_begin(date);

        if (entryAction != null) {
            entity.setDate_end(entryAction.getDate_end());
            entity.setDepth_begin(entryAction.getDepth_begin());
            entity.setDepth_end(entryAction.getDepth_end());
            entity.setDepth_length(entryAction.getDepth_begin(), entryAction.getDepth_end());
        }

        entryActionRepository.save(entity);

        return new ResponseEntity<>(entity, HttpStatus.CREATED);
    }

    @PutMapping("/entry-action/{entry_action_id}")
    public ResponseEntity<EntryAction> updateEntryAction(@PathVariable("entry_action_id") Integer entry_action_id,
                                                         @RequestParam(required = false) Integer well_equipment_id,
                                                         @RequestParam(required = false) Integer entry_action_type_id,
                                                         @RequestParam(required = false) Integer entry_action_state_id,
                                                         @RequestBody(required = false) EntryAction entryAction) throws Exception {
        Date date = new Date();

        WellEquipment wellEquipment;
        EntryActionType entryActionType;
        EntryActionState entryActionState;

        EntryAction entity = entryActionRepository.findById(entry_action_id)
                .orElseThrow(() -> new Exception("Not found [entry_action] with id = " + entry_action_id));

        if (well_equipment_id != null) {
            wellEquipment = wellEquipmentRepository.findById(well_equipment_id)
                    .orElseThrow(() -> new Exception("Not found [well_equipment] with id = " + well_equipment_id));
            entity.setWellEquipment(wellEquipment);
        }

        if (entry_action_type_id != null) {
            entryActionType = entryActionTypeRepository.findById(entry_action_type_id)
                    .orElseThrow(() -> new Exception("Not found [entry_action_type] with id = " + entry_action_type_id));
            entity.setEntryActionType(entryActionType);
        }

        if (entry_action_state_id != null) {
            entryActionState = entryActionStateRepository.findById(entry_action_state_id)
                    .orElseThrow(() -> new Exception("Not found [entry_action_state] with id = " + entry_action_state_id));
            entity.setEntryActionState(entryActionState);
        }

        if (entryAction != null) {
            entity.setDate_end(date);
            entity.setDepth_begin(entryAction.getDepth_begin());
            entity.setDepth_end(entryAction.getDepth_end());
            entity.setDepth_length(entryAction.getDepth_begin(), entryAction.getDepth_end());
        }

        entryActionRepository.save(entity);

        return new ResponseEntity<>(entity, HttpStatus.OK);
    }

    @DeleteMapping("/entry-action/{entry_action_id}")
    public ResponseEntity<EntryAction> deleteEntryAction(@PathVariable("entry_action_id") Integer entry_action_id) {

        entryActionRepository.deleteById(entry_action_id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
