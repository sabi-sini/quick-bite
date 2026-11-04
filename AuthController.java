package com.quickbite.controller;

import com.quickbite.model.User;
import com.quickbite.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthController {
    private final UserRepository users;
    private final PasswordEncoder encoder;

    public AuthController(UserRepository users, PasswordEncoder encoder) {
        this.users = users; this.encoder = encoder;
    }

    @GetMapping("/login")
    public String login() { return "login"; }

    @GetMapping("/register")
    public String registerForm() { return "register"; }

    @PostMapping("/register")
    public String register(@RequestParam String name, @RequestParam String email,
                           @RequestParam String password, @RequestParam String address, Model model) {
        if (password.length() < 6) {
            model.addAttribute("error", "Password must be at least 6 characters.");
            return "register";
        }
        if (users.findByEmail(email).isPresent()) {
            model.addAttribute("error", "That email is already registered.");
            return "register";
        }
        User u = new User();
        u.setName(name); u.setEmail(email); u.setAddress(address);
        u.setPassword(encoder.encode(password));
        users.save(u);
        return "redirect:/login?registered";
    }
}
