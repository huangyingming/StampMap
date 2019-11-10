package com.example.StampMap;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RegistrationController {

    @GetMapping("/registration")
    public String registrationForm(Model model) {
        model.addAttribute("place", new Place());
        return "registration";
    }

    @PostMapping("/registration")
    public String registrationSubmit() {
        return "registration";
    }

}