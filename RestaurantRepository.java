package com.quickbite.repository;

import com.quickbite.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
    List<Restaurant> findTop4ByOrderByRatingDesc();
    List<Restaurant> findByNameContainingIgnoreCaseOrCuisineContainingIgnoreCaseOrCategoryContainingIgnoreCase(
            String name, String cuisine, String category);
}
