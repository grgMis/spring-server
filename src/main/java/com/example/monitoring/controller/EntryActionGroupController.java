package com.example.monitoring.controller;

import com.example.monitoring.model.EntryActionGroup;
import com.example.monitoring.repository.EntryActionGroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/api")
public class EntryActionGroupController {

    @Autowired
    private EntryActionGroupRepository entryActionGroupRepository;

    @GetMapping("/entry-action-group")
    public ResponseEntity<List<EntryActionGroup>> getListEntryActionGroup(@RequestParam(required = false) String entry_action_group_name) {
        List<EntryActionGroup> entryActionGroups = new ArrayList<>();

        if (entry_action_group_name == null) {
            entryActionGroups.addAll(entryActionGroupRepository.findAll());
        } else {
            entryActionGroups.addAll(entryActionGroupRepository.findByEntry_action_group_name(entry_action_group_name));
        }

        if (entryActionGroups.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(entryActionGroups, HttpStatus.OK);
    }

    @GetMapping("/entry-action-group/{entry_action_group_id}")
    public ResponseEntity<EntryActionGroup> getEntryActionGroupById(@PathVariable("entry_action_group_id") Integer entry_action_group_id) throws Exception {
        EntryActionGroup entryActionGroup = entryActionGroupRepository.findById(entry_action_group_id)
                .orElseThrow(() -> new Exception("Not found [entry_action_group] with id = " + entry_action_group_id));
        return new ResponseEntity<>(entryActionGroup, HttpStatus.OK);
    }

    @PostMapping("/entry-action-group")
    public ResponseEntity<EntryActionGroup> createEntryActionGroup(@RequestBody EntryActionGroup entryActionGroup) {
        EntryActionGroup entity = entryActionGroupRepository
                .save(new EntryActionGroup(entryActionGroup.getEntry_action_group_name(), entryActionGroup.getEntry_action_group_sysname()));
        return new ResponseEntity<>(entity, HttpStatus.CREATED);
    }

    @PutMapping("/entry-action-group/{entry_action_group_id}")
    public ResponseEntity<EntryActionGroup> updateEntryActionGroup(@PathVariable("entry_action_group_id") Integer entry_action_group_id,
                                                                   @RequestBody EntryActionGroup entryActionGroup) throws Exception {
        EntryActionGroup entity = entryActionGroupRepository.findById(entry_action_group_id)
                .orElseThrow(() -> new Exception("Not found [entry_action_group] with id = " + entry_action_group_id));

        entity.setEntry_action_group_name(entryActionGroup.getEntry_action_group_name());
        entity.setEntry_action_group_sysname(entryActionGroup.getEntry_action_group_sysname());

        return new ResponseEntity<>(entryActionGroupRepository.save(entity), HttpStatus.OK);
    }

    @DeleteMapping("/entry-action-group/{entry_action_group_id}")
    public ResponseEntity<HttpStatus> deleteEntryActionGroup(@PathVariable("entry_action_group_id") Integer entry_action_group_id) {
        entryActionGroupRepository.deleteById(entry_action_group_id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
