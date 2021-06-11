package com.example.javaapp;

import com.example.javaapp.Model.User;
import com.example.javaapp.Repository.UserRepository;
import com.example.javaapp.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ApplicationController {

    @Autowired
    private UserService userService;

    public String getCurrentUsername() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    public List<User> getOtherUser() {
        ArrayList<User> res = new ArrayList<>();
        for (User u : userService.getUser()) {
            if (!u.getUsername().equals(getCurrentUsername())) {
                res.add(u);
            }
        }
        return res;
    }

    @GetMapping(value = "/index")
    public String homePage(Model model) {
        model.addAttribute("userList", getOtherUser());
        return "index";
    }

    @GetMapping(value = "/login")
    public String login() {
        User user = getPrincipal();
        if (user != null) {
            return "index";
        }
        return "login";
    }
    public User getPrincipal() {
        User user = null;
        if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof User) {
            user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        }
        return user;
    }
    @GetMapping(value = "/signup")
    public String signup(@ModelAttribute User user, Model model) {
        model.addAttribute("user", user);
        return "signup";
    }
}
