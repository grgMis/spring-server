package com.example.monitoring.controller;

import com.example.monitoring.model.UserRole;
import com.example.monitoring.repository.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/api")
public class UserRoleController {

    @Autowired
    private UserRoleRepository userRoleRepository;

    @GetMapping("/user-role")
    public ResponseEntity<List<UserRole>> getListUserRole(@RequestParam(required = false) String user_role_name) {

        try {
            List<UserRole> userRoles = new ArrayList<>();

            if (user_role_name == null) {
                userRoles.addAll(userRoleRepository.findAll());
            } else {
                userRoles.addAll(userRoleRepository.findByUser_role_name(user_role_name));
            }

            if (userRoles.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(userRoles, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/user-role/{user_role_id}")
    public ResponseEntity<UserRole> getUserRoleById(@PathVariable("user_role_id") Integer user_role_id) {

        try {
            UserRole userRole = userRoleRepository.findById(user_role_id)
                    .orElseThrow(() -> new Exception("Not found [user_role] with id = " + user_role_id));

            return new ResponseEntity<>(userRole, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/user-role")
    public ResponseEntity<UserRole> createUserRole(@RequestBody UserRole userRole) {

        try {
            UserRole entity = userRoleRepository
                    .save(new UserRole(userRole.getUser_role_name()));

            return new ResponseEntity<>(entity, HttpStatus.CREATED);
        } catch (Exception ex) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/user-role/{user_role_id}")
    public ResponseEntity<UserRole> updateUserRole(@PathVariable("user_role_id") Integer user_role_id,
                                                     @RequestBody UserRole userRole) {

        try {
            UserRole entity = userRoleRepository.findById(user_role_id)
                    .orElseThrow(() -> new Exception("Not found [user_role] with id = " + user_role_id));

            entity.setUser_role_name(userRole.getUser_role_name());

            userRoleRepository.save(entity);

            return new ResponseEntity<>(entity, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/user-role/{user_role_id}")
    public ResponseEntity<HttpStatus> deleteUserRole(@PathVariable("user_role_id") Integer user_role_id) {

        try {
            userRoleRepository.findById(user_role_id)
                    .orElseThrow(() -> new Exception("Not found [user_role] with id = " + user_role_id));

            userRoleRepository.deleteById(user_role_id);

            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
