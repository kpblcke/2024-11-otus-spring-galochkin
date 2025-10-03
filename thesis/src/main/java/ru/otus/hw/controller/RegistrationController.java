package ru.otus.hw.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.otus.hw.dto.NewUserDTO;
import ru.otus.hw.services.UserService;

@Controller
@AllArgsConstructor
public class RegistrationController {

    private final UserService userService;

    @GetMapping("/register")
    public String showAuthenticationForm() {
        return "register";
    }

    @PostMapping("/perform-registration")
    public String registerUser(@ModelAttribute("user") NewUserDTO userDto) {
        try {
            userService.registerNewUser(userDto);
            return "redirect:/login?registrationSuccess";
        } catch (Exception e) {
            return "redirect:/register?error";
        }
    }
}
