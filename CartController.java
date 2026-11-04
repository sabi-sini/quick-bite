package com.quickbite.controller;

import com.quickbite.repository.MenuItemRepository;
import com.quickbite.service.Cart;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@Controller
@RequestMapping("/cart")
public class CartController {
    private final Cart cart;
    private final MenuItemRepository items;

    public CartController(Cart cart, MenuItemRepository items) { this.cart = cart; this.items = items; }

    @GetMapping
    public String view(Model model) {
        model.addAttribute("cart", cart);
        return "cart";
    }

    @PostMapping("/add/{id}")
    public String add(@PathVariable Long id, @RequestParam(defaultValue = "/") String back) {
        items.findById(id).ifPresent(cart::add);
        return "redirect:" + (back.startsWith("/") && !back.startsWith("//") ? back : "/");
    }

    /** Used by the "Add to Cart" buttons (fetch/AJAX) so the page does not reload. */
    @PostMapping("/api/add/{id}")
    @ResponseBody
    public Map<String, Object> apiAdd(@PathVariable Long id) {
        items.findById(id).ifPresent(cart::add);
        return Map.of("count", cart.getCount());
    }

    @PostMapping("/dec/{id}")
    public String dec(@PathVariable Long id) { cart.decrease(id); return "redirect:/cart"; }

    @PostMapping("/remove/{id}")
    public String remove(@PathVariable Long id) { cart.remove(id); return "redirect:/cart"; }
}
