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
    }

    @GetMapping("/field/{field_id}")
    public ResponseEntity<Field> getFieldById(@PathVariable("field_id") Integer field_id) throws Exception {
        Field field = fieldRepository.findById(field_id)
                .orElseThrow(() -> new Exception("Not found [field] with id = " + field_id));
        return new ResponseEntity<>(field, HttpStatus.OK);
    }

    @PostMapping("/field")
    public ResponseEntity<Field> createField(@RequestBody Field field) {
        Field entity = fieldRepository
                .save(new Field(field.getField_name()));
        return new ResponseEntity<>(entity, HttpStatus.CREATED);
    }

    @PutMapping("/field/{field_id}")
    public ResponseEntity<Field> updateField(@PathVariable("field_id") Integer field_id,
                                             @RequestBody Field field) throws Exception {
        Field entity = fieldRepository.findById(field_id)
                .orElseThrow(() -> new Exception("Not found [field] with id = " + field_id));

        entity.setField_name(field.getField_name());

        return new ResponseEntity<>(fieldRepository.save(entity), HttpStatus.OK);
    }

    @DeleteMapping("/field/{field_id}")
    public ResponseEntity<HttpStatus> deleteField(@PathVariable("field_id") Integer field_id) {

        fieldRepository.deleteById(field_id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
