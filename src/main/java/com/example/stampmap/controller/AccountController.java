package com.example.stampmap.controller;


import com.example.stampmap.dao.UserDao;
import com.example.stampmap.dto.User;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class AccountController {
    @Autowired
    private UserDao userDao;
    
    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("user", new User());
        return "login";
    }
    
    @PostMapping("/login")
    public String login(@ModelAttribute User user, HttpSession session) {
        
        User loggedInUser;
        loggedInUser = userDao.checkLogin(user.getUserName(), user.getPassword());
        if (loggedInUser == null) {
            return "login";
        }
        session.setAttribute("user", loggedInUser);
        return "redirect:/map";
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
            User addedUser = userDao.addUser(user);
            session.setAttribute("user", addedUser);
            return "redirect:/map";
        }
    }
}
