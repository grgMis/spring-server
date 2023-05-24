package com.example.monitoring.controller;

import com.example.monitoring.model.EquipmentCategory;
import com.example.monitoring.model.EquipmentClass;
import com.example.monitoring.model.User;
import com.example.monitoring.model.UserRole;
import com.example.monitoring.repository.UserRepository;
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
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;

    @GetMapping("/user")
    public ResponseEntity<List<User>> getListUser(@RequestParam(required = false) Integer user_role_id) throws Exception {

        List<User> users = new ArrayList<>();
        UserRole userRole;

        if (user_role_id != null) {
            userRole = userRoleRepository.findById(user_role_id)
                    .orElseThrow(() -> new Exception("Not found [user_role] with id = " + user_role_id));
            users.addAll(userRepository.findByUserRole(userRole));
        } else {
            users.addAll(userRepository.findAll());
        }

        if (users.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/user/{user_id}")
    public ResponseEntity<User> getUserById(@PathVariable("user_id") Integer user_id) throws Exception {
        User user = userRepository.findById(user_id)
                .orElseThrow(() -> new Exception("Not found [user] with id = " + user_id));
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping("/user")
    public ResponseEntity<User> createUser(@RequestParam Integer user_role_id,
                                           @RequestBody User user) throws Exception {
        User entity = userRoleRepository.findById(user_role_id).map(userRole -> {
            user.setUserRole(userRole);
            return userRepository.save(user);
        }).orElseThrow(() -> new Exception("Not found [user_role] with id = " + user_role_id));

        return new ResponseEntity<>(entity, HttpStatus.CREATED);
    }

    @PutMapping("/user/{user_id}")
    public ResponseEntity<User> updateUser(@PathVariable("user_id") Integer user_id,
                                           @RequestParam(required = false) Integer user_role_id,
                                           @RequestBody User user) throws Exception {
        User entity;

        entity = userRepository.findById(user_id)
                .orElseThrow(() -> new Exception("Not found [user] with id = " + user_id));

        if (user_role_id != null) {
            UserRole userRole = userRoleRepository.findById(user_role_id)
                    .orElseThrow(() -> new Exception("Not found [user_role_id] with id = " + user_role_id));

            entity.setUserRole(userRole);
        }
        entity.setUser_father_name(user.getUser_father_name());
        entity.setUser_first_name(user.getUser_first_name());
        entity.setUser_last_name(user.getUser_last_name());
        entity.setUser_login(user.getUser_login());
        entity.setUser_password(user.getUser_password());

        return new ResponseEntity<>(userRepository.save(entity), HttpStatus.OK);
    }

    @DeleteMapping("/user/{user_id}")
    public ResponseEntity<User> deleteUser(@PathVariable("user_id") Integer user_id) {
        userRepository.deleteById(user_id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
