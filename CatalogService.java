package com.quickbite.service;

import com.quickbite.model.MenuItem;
import com.quickbite.model.Restaurant;
import com.quickbite.repository.MenuItemRepository;
import com.quickbite.repository.RestaurantRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.util.List;

/** Read-only queries used by the public pages. */
@Service
public class CatalogService {

    @lombok.Value
    public static class CategoryTile {
        String name;
        String image;
    }

    public static final List<CategoryTile> CATEGORIES = List.of(
        new CategoryTile("South Indian", "/images/categories/south-indian.jpg"),
        new CategoryTile("North Indian", "/images/categories/north-indian.jpg"),
        new CategoryTile("Chinese", "/images/categories/chinese.jpg"),
        new CategoryTile("Fast Food", "/images/categories/fast-food.jpg"),
        new CategoryTile("Pizza & Burgers", "/images/categories/pizza-burgers.jpg"),
        new CategoryTile("Bakery & Desserts", "/images/categories/bakery-desserts.jpg"),
        new CategoryTile("Healthy Food", "/images/categories/healthy-food.jpg"),
        new CategoryTile("Beverages", "/images/categories/beverages.jpg"));

    private final RestaurantRepository restaurants;
    private final MenuItemRepository menuItems;

    public CatalogService(RestaurantRepository restaurants, MenuItemRepository menuItems) {
        this.restaurants = restaurants;
        this.menuItems = menuItems;
    }

    public List<Restaurant> restaurants(String q, String category) {
        boolean hasQ = q != null && !q.isBlank();
        List<Restaurant> list = hasQ
            ? restaurants.findByNameContainingIgnoreCaseOrCuisineContainingIgnoreCaseOrCategoryContainingIgnoreCase(q, q, q)
            : restaurants.findAll(Sort.by(Sort.Direction.DESC, "rating"));
        if (category != null && !category.isBlank()) {
            list = list.stream().filter(r -> category.equalsIgnoreCase(r.getCategory())).toList();
        }
        return list;
    }

    public List<Restaurant> featured() { return restaurants.findTop4ByOrderByRatingDesc(); }

    public List<MenuItem> bestSellers() { return menuItems.findTop8ByBestSellerTrueOrderByReviewCountDesc(); }

    public List<MenuItem> searchDishes(String q) {
        return menuItems.findByNameContainingIgnoreCaseOrCategoryContainingIgnoreCase(q, q);
    }

    public Restaurant restaurant(Long id) { return restaurants.findById(id).orElseThrow(); }

    public List<String> menuCategories(Restaurant r) {
        return r.getMenuItems().stream().map(MenuItem::getCategory).distinct().toList();
    }
}
