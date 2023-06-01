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

        try {
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
        } catch (Exception ex) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/well-state/{well_state_id}")
    public ResponseEntity<WellState> getWellStateById(@PathVariable("well_state_id") Integer well_state_id) {

        try {
            WellState wellState = wellStateRepository.findById(well_state_id)
                    .orElseThrow(() -> new Exception("Not found [well_state] with id = " + well_state_id));

            return new ResponseEntity<>(wellState, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/well-state")
    public ResponseEntity<WellState> createWellState(@RequestBody WellState wellState) {

        try {
            WellState entity = wellStateRepository
                    .save(new WellState(wellState.getWell_state_name()));

            return new ResponseEntity<>(entity, HttpStatus.CREATED);
        } catch (Exception ex) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/well-state/{well_state_id}")
    public ResponseEntity<WellState> updateWellState(@PathVariable("well_state_id") Integer well_state_id,
                                                     @RequestBody WellState wellState) {

        try {
            WellState entity = wellStateRepository.findById(well_state_id)
                    .orElseThrow(() -> new Exception("Not found [well_state] with id = " + well_state_id));

            entity.setWell_state_name(wellState.getWell_state_name());

            wellStateRepository.save(entity);

            return new ResponseEntity<>(entity, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/well-state/{well_state_id}")
    public ResponseEntity<HttpStatus> deleteWellState(@PathVariable("well_state_id") Integer well_state_id) {

        try {
            wellStateRepository.findById(well_state_id)
                    .orElseThrow(() -> new Exception("Not found [well_state] with id = " + well_state_id));

            wellStateRepository.deleteById(well_state_id);

            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
