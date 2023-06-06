package com.example.monitoring.controller;

import com.example.monitoring.model.*;
import com.example.monitoring.repository.EmployeePostRepository;
import com.example.monitoring.repository.EmployeeRepository;
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

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private EmployeePostRepository employeePostRepository;

    @GetMapping("/user")
    public ResponseEntity<List<User>> getListUser(@RequestParam(required = false) Integer user_role_id,
                                                  @RequestParam(required = false) Integer employee_id,
                                                  @RequestParam(required = false) Integer employee_post_id) {

        try {
            List<User> users = new ArrayList<>();
            Employee employee;
            UserRole userRole;
            EmployeePost employeePost;

            if (user_role_id == null && employee_id == null && employee_post_id == null) {
                users.addAll(userRepository.findAll());
            }

            if (user_role_id != null) {
                userRole = userRoleRepository.findById(user_role_id)
                        .orElseThrow(() -> new Exception("Not found [user_role] with id = " + user_role_id));
                users.addAll(userRepository.findByUserRole(userRole));
            }

            if (employee_id != null) {
                employee = employeeRepository.findById(employee_id)
                        .orElseThrow(() -> new Exception("Not found [employee] with id = " + employee_id));
                users.addAll(userRepository.findByEmployee(employee));
            }
            if (employee_post_id != null) {
                employeePost = employeePostRepository.findById(employee_post_id)
                        .orElseThrow(() -> new Exception("Not found [employee_post] with id = " + employee_post_id));
                users.addAll(userRepository.findByEmployee_EmployeePost(employeePost));
            }

            if (users.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(users, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/user/auth")
    public ResponseEntity<User> getUserByLogin(@RequestParam String user_login) {
        try {
            User user;

            user = userRepository.findByUser_login(user_login);

            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/user/{user_id}")
    public ResponseEntity<User> getUserById(@PathVariable("user_id") Integer user_id) {

        try {
            User user = userRepository.findById(user_id)
                    .orElseThrow(() -> new Exception("Not found [user] with id = " + user_id));

            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/user")
    public ResponseEntity<User> createUser(@RequestParam Integer user_role_id,
                                           @RequestParam Integer employee_id,
                                           @RequestBody User user) {

        try {
            UserRole userRole = userRoleRepository.findById(user_role_id)
                    .orElseThrow(() -> new Exception("Not found [user_role] with id = " + user_role_id));

            Employee employee = employeeRepository.findById(employee_id)
                    .orElseThrow(() -> new Exception("Not found [employee] with id = " + employee_id));

            user.setUserRole(userRole);
            user.setEmployee(employee);

            userRepository.save(user);

            return new ResponseEntity<>(user, HttpStatus.CREATED);
        } catch (Exception ex) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/user/{user_id}")
    public ResponseEntity<User> updateUser(@PathVariable("user_id") Integer user_id,
                                           @RequestParam(required = false) Integer user_role_id,
                                           @RequestParam(required = false) Integer employee_id,
                                           @RequestBody User user) {

        try {
            User entity;

            entity = userRepository.findById(user_id)
                    .orElseThrow(() -> new Exception("Not found [user] with id = " + user_id));

            if (user_role_id != null) {
                UserRole userRole = userRoleRepository.findById(user_role_id)
                        .orElseThrow(() -> new Exception("Not found [user_role_id] with id = " + user_role_id));

                entity.setUserRole(userRole);
            }

            if (employee_id != null) {
                Employee employee = employeeRepository.findById(employee_id)
                        .orElseThrow(() -> new Exception("Not found [employee] with id = " + employee_id));

                entity.setEmployee(employee);
            }

            entity.setUser_login(user.getUser_login());
            entity.setUser_password(user.getUser_password());

            userRepository.save(entity);

            return new ResponseEntity<>(entity, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/user/{user_id}")
    public ResponseEntity<User> deleteUser(@PathVariable("user_id") Integer user_id) {

        try {
            userRepository.findById(user_id)
                    .orElseThrow(() -> new Exception("Not found [user] with id = " + user_id));

            userRepository.deleteById(user_id);

            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
