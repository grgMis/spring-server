package com.example.monitoring.controller;

import com.example.monitoring.model.EntryActionState;
import com.example.monitoring.repository.EntryActionStateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/api")
public class EntryActionStateController {

    @Autowired
    private EntryActionStateRepository entryActionStateRepository;

    @GetMapping("/entry-action-state")
    public ResponseEntity<List<EntryActionState>> getListEntryActionState(@RequestParam(required = false) String entry_action_state_name) {
        List<EntryActionState> entryActionStates = new ArrayList<>();

        if (entry_action_state_name == null) {
            entryActionStates.addAll(entryActionStateRepository.findAll());
        } else {
            entryActionStates.addAll(entryActionStateRepository.findByEntry_action_state_name(entry_action_state_name));
        }

        if (entryActionStates.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(entryActionStates, HttpStatus.OK);
    }

    @GetMapping("/entry-action-state/{entry_action_state_id}")
    public ResponseEntity<EntryActionState> getEntryActionStateById(@PathVariable("entry_action_state_id") Integer entry_action_state_id) throws Exception {
        EntryActionState entryActionState = entryActionStateRepository.findById(entry_action_state_id)
                .orElseThrow(() -> new Exception("Not found [entry_action_state] with id = " + entry_action_state_id));
        return new ResponseEntity<>(entryActionState, HttpStatus.OK);
    }

    @PostMapping("/entry-action-state")
    public ResponseEntity<EntryActionState> createEntryActionState(@RequestBody EntryActionState entryActionState) {
        EntryActionState entity = entryActionStateRepository
                .save(new EntryActionState(entryActionState.getEntry_action_state_name()));
        return new ResponseEntity<>(entity, HttpStatus.CREATED);
    }

    @PutMapping("/entry-action-state/{entry_action_state_id}")
    public ResponseEntity<EntryActionState> updateEntryActionState(@PathVariable("entry_action_state_id") Integer entry_action_state_id,
                                                                   @RequestBody EntryActionState entryActionState) throws Exception {
        EntryActionState entity = entryActionStateRepository.findById(entry_action_state_id)
                .orElseThrow(() -> new Exception("Not found [entry_action_state] with id = " + entry_action_state_id));

        entity.setEntry_action_state_name(entryActionState.getEntry_action_state_name());

        return new ResponseEntity<>(entryActionStateRepository.save(entity), HttpStatus.OK);
    }

    @DeleteMapping("/entry-action-state/{entry_action_state_id}")
    public ResponseEntity<HttpStatus> deleteEntryActionState(@PathVariable("entry_action_state_id") Integer entry_action_state_id) {
        entryActionStateRepository.deleteById(entry_action_state_id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
