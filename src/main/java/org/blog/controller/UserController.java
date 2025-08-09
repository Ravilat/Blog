package org.blog.controller;

import org.blog.DTO.UserProfileDto;
import org.blog.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{userName}")
    public String showInfoUser(@PathVariable("userName") String userName, Model model, Authentication auth) {
        String currentUserName;
        if (auth != null && auth.isAuthenticated()) {
            currentUserName = auth.getName();
        } else {
            currentUserName = "";
        }
        UserProfileDto user = userService.findByLoginDto(userName, currentUserName);

        model.addAttribute("user", user);

        return "user";
    }

    @PostMapping("/change-email")
    public String changeEmail(@RequestParam("newEmail") String newEmail,
                              @ModelAttribute("user") UserProfileDto userProfileDto) {

        userService.changeEmail(userProfileDto, newEmail);

        return "redirect:/user/" + userProfileDto.login();
    }

    @PostMapping("/change-password")
    public String changePassword(@RequestParam("password") String newPassword,
                                 @ModelAttribute("user") UserProfileDto userProfileDto) {

        userService.changePassword(userProfileDto, newPassword);

        return "redirect:/user/" + userProfileDto.login();
    }

}
