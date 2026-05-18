package com.finance.tracker.controller;

import com.finance.tracker.dao.CategoryDao;
import com.finance.tracker.dao.UserDao;
import com.finance.tracker.model.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthController {

    private final UserDao userDao;
    private final CategoryDao categoryDao;
    private final PasswordEncoder passwordEncoder;

    public AuthController(UserDao userDao, CategoryDao categoryDao, PasswordEncoder passwordEncoder) {
        this.userDao = userDao;
        this.categoryDao = categoryDao;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @PostMapping("/api/register")
    public String registerUser(@RequestParam String username, @RequestParam String password, Model model) {
        if (userDao.findByUsername(username) != null) {
            model.addAttribute("error", "Username already exists!");
            return "register";
        }
        User u = new User();
        u.setUsername(username);
        u.setPassword(passwordEncoder.encode(password));
        u.setRole("ROLE_USER");
        userDao.save(u);
        User savedUser = userDao.findByUsername(username);
        categoryDao.createDefaultCategories(savedUser.getId());
        return "redirect:/login?registered";
    }
}
