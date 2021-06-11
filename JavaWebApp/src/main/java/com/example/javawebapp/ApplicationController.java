package com.example.javawebapp;

import com.example.javawebapp.model.User;
import com.example.javawebapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
public class ApplicationController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping(value = "/login")
    public String loginPage() {

        return "login";
    }
    public User getPrincipal() {
        User user = null;
        if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof User) {
            user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        }
        System.out.println(SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        return user;
    }

    public boolean checkLogin(User user) {
        if (userRepository.getAllUser().size() > 0) {
            for (User u : userRepository.getAllUser()) {
                if (u.getUsername().equals(user.getUsername()) && u.getPassword().equals(user.getPassword())) {
                    return true;
                }
            }
        }
        return false;
    }
    @GetMapping(value = "/index")
    public String goHome() {
        return "index";
    }

    @GetMapping(value = "/signup")
    public String signup(@ModelAttribute User user, Model model) {
        model.addAttribute("user", user);
        return "signup";
    }
    @GetMapping(value = "/logout")
    public String logout() {return "login"; }

    @GetMapping(value = "/list")
    public String list(Model model) {
        model.addAttribute("userList", userRepository.getAllUser());
        return "list";
    }
    @GetMapping(value = "/add")
    public String add(@ModelAttribute User user, Model model) {
        model.addAttribute("user", user);
        return "add";
    }
    @PostMapping(value = "/add")
    public String addUser(@Valid User user, BindingResult bindingResult) {
        if (userRepository.checkExist(user.getUsername()))
            bindingResult.addError(new FieldError("user", "username", "Username is already taken"));

        if (bindingResult.hasErrors())
            return "add";

        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setId(userRepository.getUserList().size() + 1);
        userRepository.getUserList().put(user.getId(), user);
        System.out.println(userRepository.getAllUser());
        return "redirect:/index";
    }
    @RequestMapping(value = "/delete/{id}")
    public String deleteUser(@PathVariable Integer id) {
        userRepository.deleteUser(id);
        return "redirect:/list";
    }
    @PostMapping("/signup")
    public String save(@Valid User user, BindingResult bindingResult, RedirectAttributes ra) {

        if (userRepository.checkExist(user.getUsername()))
            bindingResult.addError(new FieldError("user", "username", "Username is already taken"));

        if (bindingResult.hasErrors())
            return "signup";

        ra.addFlashAttribute("message", "Register successfully");
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setId(userRepository.getUserList().size() + 1);
        userRepository.getUserList().put(user.getId(), user);
        System.out.println(userRepository.getAllUser());
        return "redirect:/login";
    }
    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public String editUser(@RequestParam("id") Integer id, Model model) {
        User user = userRepository.findUser(id);
        model.addAttribute("newUser", user);
        return "edit";
    }
    @RequestMapping(value = "/edit/save", method = RequestMethod.POST)
    public String saveNewUser(User user, BindingResult result) {
        if (userRepository.checkExist(user.getUsername()))
            result.addError(new FieldError("user", "username", "Username is already taken"));

        if (result.hasErrors())
            return "edit";
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setId(userRepository.getUserList().size() + 1);
        userRepository.getUserList().put(user.getId(), user);
        return "list";
    }
}
