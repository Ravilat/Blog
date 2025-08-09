package org.blog.controller;

import org.blog.DTO.UserCreateDto;
import org.blog.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/register")
public class RegistrationController {

    private final UserService userService;

    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new UserCreateDto("", "", ""));
        return "register";
    }

    @PostMapping
    public String processRegistration(@ModelAttribute("user") UserCreateDto userCreateDto) {

        userService.createUser(userCreateDto);
        return "redirect:/posts";
    }

}
