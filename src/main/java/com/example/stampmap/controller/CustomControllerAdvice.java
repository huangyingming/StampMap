
package com.example.stampmap.controller;

import com.example.stampmap.Utility;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.ui.Model;

@ControllerAdvice
public class CustomControllerAdvice {
    @ModelAttribute
    public void preprocessRequest(Model model) {
        if (Utility.isLoggedIn()) {
            model.addAttribute("isLoggedIn", true);
        } else {
            model.addAttribute("isLoggedIn", false);
        }
    }
}
