package com.example.javaapp.Controller;

import com.example.javaapp.Model.User;
import com.example.javaapp.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import javax.validation.Valid;

@Controller
public class UserController {


    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(value = "/signup")
    public String signUp(@Valid User user, BindingResult bindingResult, RedirectAttributes ra) {
        if (userService.checkExist(user.getUsername())) {
            bindingResult.addError(new FieldError("user", "username", "username is already taken"));
        }
        if (bindingResult.hasErrors()) {
            return "signup";
        }
        ra.addFlashAttribute("message", "Sign up successfully");
        userService.save(user);
        return "redirect:/login";
    }
    @RequestMapping(value = "/index/delete/{id}")
    public String deleteUser(@PathVariable Integer id) {
        userService.deleteUser(id);
        return "index";
    }

}
