package com.example.monitoring.controller;

import com.example.monitoring.model.*;
import com.example.monitoring.repository.CompanyRepository;
import com.example.monitoring.repository.WellRepository;
import com.example.monitoring.repository.WellStateRepository;
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
public class WellController {

    @Autowired
    private WellRepository wellRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private WellStateRepository wellStateRepository;

    @GetMapping("/well")
    public ResponseEntity<List<Well>> getListWell(@RequestParam(required = false) String well_name,
                                                  @RequestParam(required = false) Integer company_id,
                                                  @RequestParam(required = false) Integer well_state_id) throws Exception {

        List<Well> wells = new ArrayList<>();

        Company company;
        WellState wellState;

        if (well_name != null) {
            wells.addAll(wellRepository.findByWell_name(well_name));
            return new ResponseEntity<>(wells, HttpStatus.OK);
        }

        if (company_id != null) {
            company = companyRepository.findById(company_id)
                    .orElseThrow(() -> new Exception("Not found [company] with id = " + company_id));
            wells.addAll(wellRepository.findByCompany(company));
            return new ResponseEntity<>(wells, HttpStatus.OK);
        }

        if (well_state_id != null) {
            wellState = wellStateRepository.findById(well_state_id)
                    .orElseThrow(() -> new Exception("Not found [well_state] with id = " + well_state_id));
            wells.addAll(wellRepository.findByWellState(wellState));
            return new ResponseEntity<>(wells, HttpStatus.OK);
        }

        if (well_name == null && company_id == null && well_state_id == null) {
            wells.addAll(wellRepository.findAll());
            return new ResponseEntity<>(wells, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/well/{well_id}")
    public ResponseEntity<Well> getWellById(@PathVariable("well_id") Integer well_id) throws Exception {
        Well well = wellRepository.findById(well_id)
                .orElseThrow(() -> new Exception("Not found [well] with id = " + well_id));
        return new ResponseEntity<>(well, HttpStatus.OK);
    }

    @PostMapping("/well")
    public ResponseEntity<Well> createWell(@RequestParam Integer company_id,
                                           @RequestParam Integer well_state_id,
                                           @RequestBody Well well) throws Exception {

        Date date = new Date();

        Company company = companyRepository.findById(company_id)
                .orElseThrow(() -> new Exception("Not found [company] with id = " + company_id));

        WellState wellState = wellStateRepository.findById(well_state_id)
                .orElseThrow(() -> new Exception("Not found [well_state] with id = " + well_state_id));

        well.setCompany(company);
        well.setWellState((wellState));
        well.setDate_entry(date);

        wellRepository.save(well);

        return new ResponseEntity<>(well, HttpStatus.CREATED);
    }

    @PutMapping("/well/{well_id}")
    public ResponseEntity<Well> updateWell(@PathVariable("well_id") Integer well_id,
                                           @RequestParam(required = false) Integer company_id,
                                           @RequestParam(required = false) Integer well_state_id,
                                           @RequestBody(required = false) Well well) throws Exception {
        Date date = new Date();

        Company company;
        WellState wellState;

        Well entity = wellRepository.findById(well_id)
                .orElseThrow(() -> new Exception("Not found [well] with id = " + well_id));

        if (company_id != null) {
            company = companyRepository.findById(company_id)
                    .orElseThrow(() -> new Exception("Not found [company] with id = " + company_id));
            entity.setCompany(company);
        }

        if (well_state_id != null) {
            wellState = wellStateRepository.findById(well_state_id)
                    .orElseThrow(() -> new Exception("Not found [well_state] with id = " + well_state_id));
            entity.setWellState(wellState);
        }

        if (well != null) {
            entity.setWell_name(well.getWell_name());
            entity.setDate_entry(date);
        }

        wellRepository.save(entity);

        return new ResponseEntity<>(entity, HttpStatus.OK);
    }

    @DeleteMapping("/well/{well_id}")
    public ResponseEntity<Well> deleteEquipment(@PathVariable("well_id") Integer well_id) {

        wellRepository.deleteById(well_id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
