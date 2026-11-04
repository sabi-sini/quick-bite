package com.quickbite.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.math.RoundingMode;

@Entity
@Getter @Setter
public class MenuItem {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    /** dish category inside a restaurant menu, e.g. "Dosa", "Biryani" */
    private String category;
    @Column(length = 300)
    private String description;
    private BigDecimal price;
    /** original price before discount (null when there is no discount) */
    private BigDecimal originalPrice;
    private boolean veg;
    private double rating;
    private int reviewCount;
    private boolean bestSeller;
    private String imageUrl;
    @ManyToOne
    private Restaurant restaurant;

    public int getDiscountPercent() {
        if (originalPrice == null || price == null || originalPrice.compareTo(price) <= 0) return 0;
        return originalPrice.subtract(price).multiply(BigDecimal.valueOf(100))
                .divide(originalPrice, 0, RoundingMode.HALF_UP).intValue();
    }
}
