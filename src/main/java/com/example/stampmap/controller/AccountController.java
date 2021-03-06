package com.example.stampmap.controller;

import com.example.stampmap.Utility;
import com.example.stampmap.dao.UserDao;
import com.example.stampmap.dto.User;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
public class AccountController {
    @Autowired
    private UserDao userDao;
    
    @GetMapping("/login")
    public String login(Model model, RedirectAttributes redirectAttributes, @RequestParam(name = "toupload", required = false) Boolean toUpload) {
        model.addAttribute("user", new User());
        model.addAttribute("toUpload", toUpload);
        return "login";
    }
    
    @PostMapping("/login")
    public String login(@ModelAttribute User user, HttpSession session, RedirectAttributes redirectAttributes, Model model) {
        String result = processLogin(user, session, redirectAttributes, model, false);
        return result;
    }
    
    @PostMapping("/login2")
    public String login2(@ModelAttribute User user, HttpSession session, RedirectAttributes redirectAttributes, Model model) {
        String result = processLogin(user, session, redirectAttributes, model, true);
        return result;
    }
    
    private String processLogin(User user, HttpSession session, RedirectAttributes redirectAttributes, Model model, boolean is2) {
        User loggedInUser;
        loggedInUser = userDao.checkLogin(user.getUserName(), user.getPassword());
        if (loggedInUser == null) {
            model.addAttribute("hasError", true);
            return "login";
        }
        session.setAttribute("user", loggedInUser);
        if (is2) {
            return "redirect:/upload";
        } else {
            return "redirect:/map";
        }
    }
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
    @GetMapping("/registration")
    public String renderRegistration(Model model) {
        model.addAttribute("user", new User());
        return "registration";
    }
    @PostMapping("/registration")
    public String register(Model model, @Validated User user, BindingResult result, HttpSession session) {
        if (!user.isSamePassword()) {
            result.rejectValue("passwordConfirm", "passwordConfirmDifferent", "error");
        }
        if (result.hasErrors()) {
            return "registration";
        }
        else {
            try {
                User addedUser = userDao.addUser(user);
                session.setAttribute("user", addedUser);
                return "redirect:/map";
            } catch(DataIntegrityViolationException e) {
                result.rejectValue("userName", "uniqueConstraintViolation", "error");
                return "registration";
            }
        }
    }
    
    @GetMapping("/account")
    public String renderAccount(Model model) {
        if (!Utility.isLoggedIn()) return "redirect:/login";
        User user = Utility.getCurrentUser();
        model.addAttribute("user", user);
        model.addAttribute("isLoggedIn", true);
        return "registration";
    }
    
    @PostMapping("/account")
    public String update(Model model, @Validated User user, BindingResult result, HttpSession session) {
        if (!user.isSamePassword()) {
            result.rejectValue("passwordConfirm", "passwordConfirmDifferent", "error");
        }
        if (result.hasErrors()) {
            return "registration";
        }
        else {
            try {
                userDao.updateUser(user);
                session.setAttribute("user", user);
                return "redirect:/map";
            } catch(DataIntegrityViolationException e) {
                result.rejectValue("userName", "uniqueConstraintViolation", "error");
                return "registration";
            }
        }
    }
}
