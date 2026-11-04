package com.quickbite.controller;

import com.quickbite.model.ContactMessage;
import com.quickbite.repository.ContactMessageRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class ContactController {
    private final ContactMessageRepository messages;
    public ContactController(ContactMessageRepository messages) { this.messages = messages; }

    @GetMapping("/contact")
    public String form() { return "contact"; }

    @PostMapping("/contact")
    public String submit(@RequestParam String name, @RequestParam String email,
                         @RequestParam(required = false) String phone,
                         @RequestParam(required = false) String subject,
                         @RequestParam String message) {
        if (name.isBlank() || email.isBlank() || message.isBlank()) return "redirect:/contact?error";
        ContactMessage m = new ContactMessage();
        m.setName(name.trim());
        m.setEmail(email.trim());
        m.setPhone(phone);
        m.setSubject(subject);
        m.setMessage(message.length() > 2000 ? message.substring(0, 2000) : message);
        messages.save(m);
        return "redirect:/contact?sent";
    }
}
