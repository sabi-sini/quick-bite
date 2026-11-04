package com.quickbite.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Restaurant {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    /** e.g. "South Indian", "Fast Food" – used by the category filter */
    private String category;
    /** short tag line, e.g. "Dosa · Idli · Filter Coffee" */
    private String cuisine;
    @Column(length = 500)
    private String description;
    private int deliveryMinutes;
    private double rating;
    private int reviewCount;
    private String imageUrl;
    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL)
    private List<MenuItem> menuItems = new ArrayList<>();
}
