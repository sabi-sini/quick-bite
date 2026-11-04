package com.quickbite.repository;

import com.quickbite.model.MenuItem;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface MenuItemRepository extends JpaRepository<MenuItem, Long> {
    List<MenuItem> findTop8ByBestSellerTrueOrderByReviewCountDesc();
    List<MenuItem> findByNameContainingIgnoreCaseOrCategoryContainingIgnoreCase(String name, String category);
}
