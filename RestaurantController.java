package com.quickbite.controller;

import com.quickbite.model.Restaurant;
import com.quickbite.service.CatalogService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/restaurants")
public class RestaurantController {
    private final CatalogService catalog;
    public RestaurantController(CatalogService catalog) { this.catalog = catalog; }

    @GetMapping
    public String list(@RequestParam(required = false) String q,
                       @RequestParam(required = false) String category, Model model) {
        model.addAttribute("restaurants", catalog.restaurants(q, category));
        model.addAttribute("categories", CatalogService.CATEGORIES);
        model.addAttribute("q", q);
        model.addAttribute("category", category);
        return "restaurants";
    }

    @GetMapping("/{id}")
    public String menu(@PathVariable Long id, Model model) {
        Restaurant r = catalog.restaurant(id);
        model.addAttribute("restaurant", r);
        model.addAttribute("categories", catalog.menuCategories(r));
        return "restaurant";
    }
}
