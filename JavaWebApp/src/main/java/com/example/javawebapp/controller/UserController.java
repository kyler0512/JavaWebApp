package com.example.javawebapp.controller;

import com.example.javawebapp.repository.UserRepository;
import com.example.javawebapp.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

@RestController
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @GetMapping(value = "/users", produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<User> getListUser() {
        List<User> users = userRepository.getAllUser();
        return users;
    }
    @GetMapping(value = "/user/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public User getUser(@PathVariable int id) {
        return userRepository.getUser(id);
    }
    @PostMapping(value = "/user", produces = {MediaType.APPLICATION_JSON_VALUE})
    public User addUser(@RequestBody User user) {
        return userRepository.addUser(user);
    }
    @PutMapping(value = "/user", produces = {MediaType.APPLICATION_JSON_VALUE})
    public User updateUser(@RequestBody User user) {
        return userRepository.updateUser(user);
    }
    @DeleteMapping(value = "/user/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public void deleteUser(@PathVariable Integer id) {
        userRepository.deleteUser(id);
    }

}
