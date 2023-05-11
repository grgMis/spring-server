package com.example.monitoring.controller;

import com.example.monitoring.model.EntryActionGroup;
import com.example.monitoring.model.EntryActionType;
import com.example.monitoring.repository.EntryActionGroupRepository;
import com.example.monitoring.repository.EntryActionTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/api")
public class EntryActionTypeController {

    @Autowired
    private EntryActionTypeRepository entryActionTypeRepository;

    @Autowired
    private EntryActionGroupRepository entryActionGroupRepository;

    @GetMapping("/entry-action-type")
    public ResponseEntity<List<EntryActionType>> getListEntryActionType(@RequestParam(required = false) String entry_action_type_name) {
        List<EntryActionType> entryActionTypes = new ArrayList<>();

        if (entry_action_type_name == null) {
            entryActionTypes.addAll(entryActionTypeRepository.findAll());
        } else {
            entryActionTypes.addAll(entryActionTypeRepository.findByEntry_action_type_name(entry_action_type_name));
        }

        if (entryActionTypes.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(entryActionTypes, HttpStatus.OK);
    }

    @GetMapping("/entry-action-type/{entry_action_type_id}")
    public ResponseEntity<EntryActionType> getEntryActionTypeById(@PathVariable("entry_action_type_id") Integer entry_action_type_id) throws Exception {
        EntryActionType entryActionType = entryActionTypeRepository.findById(entry_action_type_id)
                .orElseThrow(() -> new Exception("Not found [entry_action_type] with id = " + entry_action_type_id));
        return new ResponseEntity<>(entryActionType, HttpStatus.OK);
    }

    @PostMapping("/entry-action-type")
    public ResponseEntity<EntryActionType> createEntryActionType(@RequestParam Integer entry_action_group_id,
                                                                 @RequestBody EntryActionType entryActionType) throws Exception {
        EntryActionType entity = entryActionGroupRepository.findById(entry_action_group_id).map(entryActionGroup -> {
            entryActionType.setEntryActionGroup(entryActionGroup);
            return entryActionTypeRepository.save(entryActionType);
        }).orElseThrow(() -> new Exception("Not found [entry_action_group] with id = " + entry_action_group_id));

        return new ResponseEntity<>(entity, HttpStatus.CREATED);
    }

    @PutMapping("/entry-action-type/{entry_action_type_id}")
    public ResponseEntity<EntryActionType> updateEntryActionType(@PathVariable("entry_action_type_id") Integer entry_action_type_id,
                                                                 @RequestParam(required = false) Integer entry_action_group_id,
                                                                 @RequestBody EntryActionType entryActionType) throws Exception {
        EntryActionType entity;

        entity = entryActionTypeRepository.findById(entry_action_type_id)
                .orElseThrow(() -> new Exception("Not found [entry_action_type] with id = " + entry_action_type_id));

        if (entry_action_group_id != null) {
            EntryActionGroup entryActionGroup = entryActionGroupRepository.findById(entry_action_group_id)
                    .orElseThrow(() -> new Exception("Not found [entry_action_group] with id = " + entry_action_group_id));

            entity.setEntryActionGroup(entryActionGroup);
        }
        entity.setEntry_action_type_name(entryActionType.getEntry_action_type_name());
        entity.setEntry_action_type_sysname(entryActionType.getEntry_action_type_sysname());

        return new ResponseEntity<>(entryActionTypeRepository.save(entity), HttpStatus.OK);
    }

    @DeleteMapping("/entry-action-type/{entry_action_type_id}")
    public ResponseEntity<EntryActionType> deleteEntryActionType(@PathVariable("entry_action_type_id") Integer entry_action_type_id) {
        entryActionTypeRepository.deleteById(entry_action_type_id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
