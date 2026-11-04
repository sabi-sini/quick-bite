package com.quickbite.controller;

import com.quickbite.model.Order;
import com.quickbite.model.OrderItem;
import com.quickbite.model.User;
import com.quickbite.repository.OrderRepository;
import com.quickbite.repository.UserRepository;
import com.quickbite.service.Cart;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.security.Principal;

@Controller
public class OrderController {
    private final Cart cart;
    private final OrderRepository orders;
    private final UserRepository users;

    public OrderController(Cart cart, OrderRepository orders, UserRepository users) {
        this.cart = cart; this.orders = orders; this.users = users;
    }

    @PostMapping("/orders")
    public String place(@RequestParam String address, Principal principal, RedirectAttributes ra) {
        if (cart.getLines().isEmpty()) return "redirect:/cart";
        User user = users.findByEmail(principal.getName()).orElseThrow();

        Order order = new Order();
        order.setUser(user);
        order.setDeliveryAddress(address);
        order.setTotal(cart.getTotal());
        for (Cart.Line l : cart.getLines()) {
            OrderItem oi = new OrderItem();
            oi.setOrder(order);
            oi.setItemName(l.getName());
            oi.setPrice(l.getPrice());
            oi.setQuantity(l.getQty());
            order.getItems().add(oi);
        }
        orders.save(order);
        cart.clear();
        ra.addFlashAttribute("placed", order.getId());
        return "redirect:/orders";
    }

    @GetMapping("/orders")
    public String history(Principal principal, Model model) {
        User user = users.findByEmail(principal.getName()).orElseThrow();
        model.addAttribute("orders", orders.findByUserOrderByCreatedAtDesc(user));
        return "orders";
    }
}
