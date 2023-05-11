package com.example.monitoring.controller;

import com.example.monitoring.model.RepairActionState;
import com.example.monitoring.repository.RepairActionStateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/api")
public class RepairActionStateController {

    @Autowired
    private RepairActionStateRepository repairActionStateRepository;

    @GetMapping("/repair-action-state")
    public ResponseEntity<List<RepairActionState>> getListRepairActionState(@RequestParam(required = false) String repair_action_state_name) {
        List<RepairActionState> repairActionStates = new ArrayList<>();

        if (repair_action_state_name == null) {
            repairActionStates.addAll(repairActionStateRepository.findAll());
        } else {
            repairActionStates.addAll(repairActionStateRepository.findByRepair_action_state_name(repair_action_state_name));
        }

        if (repairActionStates.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(repairActionStates, HttpStatus.OK);
    }

    @GetMapping("/repair-action-state/{repair_action_state_id}")
    public ResponseEntity<RepairActionState> getRepairActionStateById(@PathVariable("repair_action_state_id") Integer repair_action_state_id) throws Exception {
        RepairActionState repairActionState = repairActionStateRepository.findById(repair_action_state_id)
                .orElseThrow(() -> new Exception("Not found [repair_action_state] with id = " + repair_action_state_id));
        return new ResponseEntity<>(repairActionState, HttpStatus.OK);
    }

    @PostMapping("/repair-action-state")
    public ResponseEntity<RepairActionState> createRepairActionState(@RequestBody RepairActionState repairActionState) {
        RepairActionState entity = repairActionStateRepository
                .save(new RepairActionState(repairActionState.getRepair_action_state_name()));
        return new ResponseEntity<>(entity, HttpStatus.CREATED);
    }

    @PutMapping("/repair-action-state/{repair_action_state_id}")
    public ResponseEntity<RepairActionState> updateRepairActionState(@PathVariable("repair_action_state_id") Integer repair_action_state_id,
                                                                     @RequestBody RepairActionState repairActionState) throws Exception {
        RepairActionState entity = repairActionStateRepository.findById(repair_action_state_id)
                .orElseThrow(() -> new Exception("Not found [repair_action_state] with id = " + repair_action_state_id));

        entity.setRepair_action_state_name(repairActionState.getRepair_action_state_name());

        return new ResponseEntity<>(repairActionStateRepository.save(entity), HttpStatus.OK);
    }

    @DeleteMapping("/repair-action-state/{repair_action_state_id}")
    public ResponseEntity<HttpStatus> deleteRepairActionState(@PathVariable("entry_action_state_id") Integer entry_action_state_id) {
        repairActionStateRepository.deleteById(entry_action_state_id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
