package com.example.monitoring.controller;

import com.example.monitoring.model.WellState;
import com.example.monitoring.repository.WellStateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/api")
public class WellStateController {

    @Autowired
    private WellStateRepository wellStateRepository;

    @GetMapping("/well-state")
    public ResponseEntity<List<WellState>> getListWellState(@RequestParam(required = false) String well_state_name) {
        List<WellState> wellStates = new ArrayList<>();

        if (well_state_name == null) {
            wellStates.addAll(wellStateRepository.findAll());
        } else {
            wellStates.addAll(wellStateRepository.findByWell_state_name(well_state_name));
        }

        if (wellStates.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(wellStates, HttpStatus.OK);
    }

    @GetMapping("/well-state/{well_state_id}")
    public ResponseEntity<WellState> getWellStateById(@PathVariable("well_state_id") Integer well_state_id) throws Exception {
        WellState wellState = wellStateRepository.findById(well_state_id)
                .orElseThrow(() -> new Exception("Not found [well_state] with id = " + well_state_id));
        return new ResponseEntity<>(wellState, HttpStatus.OK);
    }

    @PostMapping("/well-state")
    public ResponseEntity<WellState> createWellState(@RequestBody WellState wellState) {
        WellState entity = wellStateRepository
                .save(new WellState(wellState.getWell_state_name()));
        return new ResponseEntity<>(entity, HttpStatus.CREATED);
    }

    @PutMapping("/well-state/{well_state_id}")
    public ResponseEntity<WellState> updateWellState(@PathVariable("well_state_id") Integer well_state_id,
                                                     @RequestBody WellState wellState) throws Exception {
        WellState entity = wellStateRepository.findById(well_state_id)
                .orElseThrow(() -> new Exception("Not found [well_state] with id = " + well_state_id));

        entity.setWell_state_name(wellState.getWell_state_name());

        return new ResponseEntity<>(wellStateRepository.save(entity), HttpStatus.OK);
    }

    @DeleteMapping("/well-state/{well_state_id}")
    public ResponseEntity<HttpStatus> deleteWellState(@PathVariable("well_state_id") Integer well_state_id) {

        wellStateRepository.deleteById(well_state_id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
