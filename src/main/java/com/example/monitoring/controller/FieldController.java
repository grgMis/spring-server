package com.example.monitoring.controller;

import com.example.monitoring.model.Field;
import com.example.monitoring.repository.FieldRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/api")
public class FieldController {

    @Autowired
    private FieldRepository fieldRepository;

    @GetMapping("/field")
    public ResponseEntity<List<Field>> getListField(@RequestParam(required = false) String field_name) {

        try {
            List<Field> fields = new ArrayList<>();

            if (field_name == null) {
                fields.addAll(fieldRepository.findAll());
            } else {
                fields.addAll(fieldRepository.findByField_name(field_name));
            }

            if (fields.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(fields, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/field/{field_id}")
    public ResponseEntity<Field> getFieldById(@PathVariable("field_id") Integer field_id) {

        try {
            Field field = fieldRepository.findById(field_id)
                    .orElseThrow(() -> new Exception("Not found [field] with id = " + field_id));

            return new ResponseEntity<>(field, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/field")
    public ResponseEntity<Field> createField(@RequestBody Field field) {

        try {
            Field entity = fieldRepository
                    .save(new Field(field.getField_name()));

            return new ResponseEntity<>(entity, HttpStatus.CREATED);
        } catch (Exception ex) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/field/{field_id}")
    public ResponseEntity<Field> updateField(@PathVariable("field_id") Integer field_id,
                                             @RequestBody Field field) {

        try {
            Field entity = fieldRepository.findById(field_id)
                    .orElseThrow(() -> new Exception("Not found [field] with id = " + field_id));

            entity.setField_name(field.getField_name());

            fieldRepository.save(entity);

            return new ResponseEntity<>(entity, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/field/{field_id}")
    public ResponseEntity<HttpStatus> deleteField(@PathVariable("field_id") Integer field_id) {

        try {
            fieldRepository.findById(field_id)
                    .orElseThrow(() -> new Exception("Not found [field] with id = " + field_id));

            fieldRepository.deleteById(field_id);

            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
