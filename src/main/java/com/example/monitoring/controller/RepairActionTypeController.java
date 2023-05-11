package com.example.monitoring.controller;

import com.example.monitoring.model.RepairActionGroup;
import com.example.monitoring.model.RepairActionType;
import com.example.monitoring.repository.RepairActionGroupRepository;
import com.example.monitoring.repository.RepairActionTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/api")
public class RepairActionTypeController {

    @Autowired
    private RepairActionTypeRepository repairActionTypeRepository;

    @Autowired
    private RepairActionGroupRepository repairActionGroupRepository;

    @GetMapping("/repair-action-type")
    public ResponseEntity<List<RepairActionType>> getListRepairActionType(@RequestParam(required = false) String repair_action_type_name) {
        List<RepairActionType> repairActionTypes = new ArrayList<>();

        if (repair_action_type_name == null) {
            repairActionTypes.addAll(repairActionTypeRepository.findAll());
        } else {
            repairActionTypes.addAll(repairActionTypeRepository.findByRepair_action_type_name(repair_action_type_name));
        }

        if (repairActionTypes.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(repairActionTypes, HttpStatus.OK);
    }

    @GetMapping("/repair-action-type/{repair_action_type_id}")
    public ResponseEntity<RepairActionType> getRepairActionTypeById(@PathVariable("repair_action_type_id") Integer repair_action_type_id) throws Exception {
        RepairActionType entryActionType = repairActionTypeRepository.findById(repair_action_type_id)
                .orElseThrow(() -> new Exception("Not found [repair_action_type] with id = " + repair_action_type_id));
        return new ResponseEntity<>(entryActionType, HttpStatus.OK);
    }

    @PostMapping("/repair-action-type")
    public ResponseEntity<RepairActionType> createRepairActionType(@RequestParam Integer repair_action_group_id,
                                                                   @RequestBody RepairActionType repairActionType) throws Exception {
        RepairActionType entity = repairActionGroupRepository.findById(repair_action_group_id).map(repairActionGroup -> {
            repairActionType.setRepairActionGroup(repairActionGroup);
            return repairActionTypeRepository.save(repairActionType);
        }).orElseThrow(() -> new Exception("Not found [repair_action_group] with id = " + repair_action_group_id));

        return new ResponseEntity<>(entity, HttpStatus.CREATED);
    }

    @PutMapping("/repair-action-type/{repair_action_type_id}")
    public ResponseEntity<RepairActionType> updateRepairActionType(@PathVariable("repair_action_type_id") Integer repair_action_type_id,
                                                                   @RequestParam(required = false) Integer repair_action_group_id,
                                                                   @RequestBody RepairActionType repairActionType) throws Exception {
        RepairActionType entity;

        entity = repairActionTypeRepository.findById(repair_action_type_id)
                .orElseThrow(() -> new Exception("Not found [repair_action_type] with id = " + repair_action_type_id));

        if (repair_action_group_id != null) {
            RepairActionGroup repairActionGroup = repairActionGroupRepository.findById(repair_action_group_id)
                    .orElseThrow(() -> new Exception("Not found [repair_action_group] with id = " + repair_action_group_id));

            entity.setRepairActionGroup(repairActionGroup);
        }
        entity.setRepair_action_type_name(repairActionType.getRepair_action_type_name());
        entity.setRepair_action_type_sysname(repairActionType.getRepair_action_type_sysname());

        return new ResponseEntity<>(repairActionTypeRepository.save(entity), HttpStatus.OK);
    }

    @DeleteMapping("/repair-action-type/{repair_action_type_id}")
    public ResponseEntity<RepairActionType> deleteRepairActionType(@PathVariable("repair_action_type_id") Integer repair_action_type_id) {
        repairActionTypeRepository.deleteById(repair_action_type_id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
