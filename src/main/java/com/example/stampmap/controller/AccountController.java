package com.example.stampmap.controller;


import com.example.stampmap.dao.UserDao;
import com.example.stampmap.dto.User;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AccountController {
    @Autowired
    private UserDao userDao;
    
    @GetMapping("/index")
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
        return "redirect:/index";
    }
    
    @GetMapping("/check")
    public String check(HttpSession session) {
        User user = (User) session.getAttribute("user");
        System.out.println(user.getUserName());
        System.out.println(user.getUserId());
        return "index";
    }
    @GetMapping("/test")
    public String test() {
        return "test";
    }
}
