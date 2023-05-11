package com.example.monitoring.controller;

import com.example.monitoring.model.EntryActionGroup;
import com.example.monitoring.model.RepairActionGroup;
import com.example.monitoring.repository.RepairActionGroupRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/api")
public class RepairActionGroupController {

    private RepairActionGroupRepository repairActionGroupRepository;

    @GetMapping("/repair-action-group")
    public ResponseEntity<List<RepairActionGroup>> getListRepairActionGroup(@RequestParam(required = false) String repair_action_group_name) {
        List<RepairActionGroup> repairActionGroups = new ArrayList<>();

        if (repair_action_group_name == null) {
            repairActionGroups.addAll(repairActionGroupRepository.findAll());
        } else {
            repairActionGroups.addAll(repairActionGroupRepository.findByRepair_action_group_name(repair_action_group_name));
        }

        if (repairActionGroups.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(repairActionGroups, HttpStatus.OK);
    }

    @GetMapping("/repair-action-group/{repair_action_group_id}")
    public ResponseEntity<RepairActionGroup> getEntryRepairActionGroupById(@PathVariable("repair_action_group_id") Integer repair_action_group_id) throws Exception {
        RepairActionGroup repairActionGroup = repairActionGroupRepository.findById(repair_action_group_id)
                .orElseThrow(() -> new Exception("Not found [repair_action_group] with id = " + repair_action_group_id));
        return new ResponseEntity<>(repairActionGroup, HttpStatus.OK);
    }

    @PostMapping("/repair-action-group")
    public ResponseEntity<RepairActionGroup> createRepairActionGroup(@RequestBody RepairActionGroup repairActionGroup) {
        RepairActionGroup entity = repairActionGroupRepository
                .save(new RepairActionGroup(repairActionGroup.getRepair_action_group_name(), repairActionGroup.getRepair_action_group_sysname()));
        return new ResponseEntity<>(entity, HttpStatus.CREATED);
    }

    @PutMapping("/repair-action-group/{repair_action_group_id}")
    public ResponseEntity<RepairActionGroup> updateRepairActionGroup(@PathVariable("repair_action_group_id") Integer repair_action_group_id,
                                                                     @RequestBody RepairActionGroup repairActionGroup) throws Exception {
        RepairActionGroup entity = repairActionGroupRepository.findById(repair_action_group_id)
                .orElseThrow(() -> new Exception("Not found [repair_action_group] with id = " + repair_action_group_id));

        entity.setRepair_action_group_name(repairActionGroup.getRepair_action_group_name());
        entity.setRepair_action_group_sysname(repairActionGroup.getRepair_action_group_sysname());

        return new ResponseEntity<>(repairActionGroupRepository.save(entity), HttpStatus.OK);
    }

    @DeleteMapping("/repair-action-group/{repair_action_group_id}")
    public ResponseEntity<HttpStatus> deleteRepairActionGroup(@PathVariable("repair_action_group_id") Integer repair_action_group_id) {
        repairActionGroupRepository.deleteById(repair_action_group_id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
