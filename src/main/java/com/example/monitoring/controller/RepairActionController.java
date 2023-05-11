package com.example.monitoring.controller;

import com.example.monitoring.model.*;
import com.example.monitoring.repository.RepairActionRepository;
import com.example.monitoring.repository.RepairActionStateRepository;
import com.example.monitoring.repository.RepairActionTypeRepository;
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
public class RepairActionController {

    @Autowired
    private RepairActionRepository repairActionRepository;

    @Autowired
    private WellEquipmentRepository wellEquipmentRepository;

    @Autowired
    private RepairActionTypeRepository repairActionTypeRepository;

    @Autowired
    private RepairActionStateRepository repairActionStateRepository;

    @GetMapping("/repair-action")
    public ResponseEntity<List<RepairAction>> getListRepairAction(@RequestParam(required = false) Integer well_equipment_id,
                                                                  @RequestParam(required = false) Integer repair_action_type_id,
                                                                  @RequestParam(required = false) Integer repair_action_state_id) throws Exception {

        List<RepairAction> repairActions = new ArrayList<>();

        WellEquipment wellEquipment;
        RepairActionType repairActionType;
        RepairActionState repairActionState;

        if (well_equipment_id != null) {
            wellEquipment = wellEquipmentRepository.findById(well_equipment_id)
                    .orElseThrow(() -> new Exception("Not found [well_equipment] with id = " + well_equipment_id));
            repairActions.addAll(repairActionRepository.findByWellEquipment(wellEquipment));
            return new ResponseEntity<>(repairActions, HttpStatus.OK);
        }

        if (repair_action_type_id != null) {
            repairActionType = repairActionTypeRepository.findById(repair_action_type_id)
                    .orElseThrow(() -> new Exception("Not found [repair_action_type] with id = " + repair_action_type_id));
            repairActions.addAll(repairActionRepository.findByRepairActionType(repairActionType));
            return new ResponseEntity<>(repairActions, HttpStatus.OK);
        }

        if (repair_action_state_id != null) {
            repairActionState = repairActionStateRepository.findById(repair_action_state_id)
                    .orElseThrow(() -> new Exception("Not found [repair_action_state] with id = " + repair_action_state_id));
            repairActions.addAll(repairActionRepository.findByRepairActionState(repairActionState));
            return new ResponseEntity<>(repairActions, HttpStatus.OK);
        }

        if (well_equipment_id == null && repair_action_type_id == null && repair_action_state_id == null) {
            repairActions.addAll(repairActionRepository.findAll());
        }

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/repair-action/{repair_action_id}")
    public ResponseEntity<RepairAction> getRepairActionById(@PathVariable("repair_action_id") Integer repair_action_id) throws Exception {
        RepairAction repairAction = repairActionRepository.findById(repair_action_id)
                .orElseThrow(() -> new Exception("Not found [repair_action] with id = " + repair_action_id));
        return new ResponseEntity<>(repairAction, HttpStatus.OK);
    }

    @PostMapping("/repair-action")
    public ResponseEntity<RepairAction> createRepairAction(@RequestParam Integer well_equipment_id,
                                                           @RequestParam Integer repair_action_type_id,
                                                           @RequestParam Integer repair_action_state_id,
                                                           @RequestBody(required = false) RepairAction repairAction) throws Exception {

        Date date = new Date();

        RepairAction entity = new RepairAction();

        WellEquipment wellEquipment = wellEquipmentRepository.findById(well_equipment_id)
                .orElseThrow(() -> new Exception("Not found [well_equipment] with id = " + well_equipment_id));

        RepairActionType repairActionType = repairActionTypeRepository.findById(repair_action_type_id)
                .orElseThrow(() -> new Exception("Not found [repair_action_type] with id = " + repair_action_type_id));

        RepairActionState repairActionState = repairActionStateRepository.findById(repair_action_state_id)
                .orElseThrow(() -> new Exception("Not found [repair_action_state] with id = " + repair_action_state_id));

        entity.setWellEquipment(wellEquipment);
        entity.setRepairActionType(repairActionType);
        entity.setRepairActionState(repairActionState);
        entity.setDate_begin(date);

        if (repairAction != null) {
            entity.setDate_end(repairAction.getDate_end());
        }

        repairActionRepository.save(entity);

        return new ResponseEntity<>(entity, HttpStatus.CREATED);
    }

    @PutMapping("/repair-action/{repair_action_id}")
    public ResponseEntity<RepairAction> updateRepairAction(@PathVariable("repair_action_id") Integer repair_action_id,
                                                           @RequestParam(required = false) Integer well_equipment_id,
                                                           @RequestParam(required = false) Integer repair_action_type_id,
                                                           @RequestParam(required = false) Integer repair_action_state_id,
                                                           @RequestBody(required = false) RepairAction repairAction) throws Exception {
        Date date = new Date();

        WellEquipment wellEquipment;
        RepairActionType repairActionType;
        RepairActionState repairActionState;

        RepairAction entity = repairActionRepository.findById(repair_action_id)
                .orElseThrow(() -> new Exception("Not found [repair_action] with id = " + repair_action_id));

        if (well_equipment_id != null) {
            wellEquipment = wellEquipmentRepository.findById(well_equipment_id)
                    .orElseThrow(() -> new Exception("Not found [well_equipment] with id = " + well_equipment_id));
            entity.setWellEquipment(wellEquipment);
        }

        if (repair_action_type_id != null) {
            repairActionType = repairActionTypeRepository.findById(repair_action_type_id)
                    .orElseThrow(() -> new Exception("Not found [repair_action_type] with id = " + repair_action_type_id));
            entity.setRepairActionType(repairActionType);
        }

        if (repair_action_state_id != null) {
            repairActionState = repairActionStateRepository.findById(repair_action_state_id)
                    .orElseThrow(() -> new Exception("Not found [repair_action_state] with id = " + repair_action_state_id));
            entity.setRepairActionState(repairActionState);
        }

        if (repairAction != null) {
            entity.setDate_end(date);
        }

        repairActionRepository.save(entity);

        return new ResponseEntity<>(entity, HttpStatus.OK);
    }

    @DeleteMapping("/repair-action/{repair_action_id}")
    public ResponseEntity<RepairAction> deleteRepairAction(@PathVariable("repair_action_id") Integer repair_action_id) {

        repairActionRepository.deleteById(repair_action_id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
