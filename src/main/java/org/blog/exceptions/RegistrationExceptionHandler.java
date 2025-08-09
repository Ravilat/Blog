package org.blog.exceptions;

import org.blog.DTO.UserCreateDto;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class RegistrationExceptionHandler {


    @ExceptionHandler(RegistrationExceptions.class)
    public String handleRegistrationException(RegistrationExceptions e, Model model){

        model.addAttribute("errorMessage", e.getMessage());
        model.addAttribute("user", new UserCreateDto("","",""));
        return "register";
    }

}
