package com.quickbite.controller;

import com.quickbite.service.CatalogService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class HomeController {
    private final CatalogService catalog;
    public HomeController(CatalogService catalog) { this.catalog = catalog; }

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("categories", CatalogService.CATEGORIES);
        model.addAttribute("featured", catalog.featured());
        model.addAttribute("bestSellers", catalog.bestSellers());
        return "index";
    }

    @GetMapping("/about")
    public String about() { return "about"; }

    @GetMapping("/search")
    public String search(@RequestParam(defaultValue = "") String q, Model model) {
        model.addAttribute("q", q);
        if (q.isBlank()) {
            model.addAttribute("restaurants", java.util.List.of());
            model.addAttribute("dishes", java.util.List.of());
        } else {
            model.addAttribute("restaurants", catalog.restaurants(q, null));
            model.addAttribute("dishes", catalog.searchDishes(q));
        }
        return "search";
    }
}
