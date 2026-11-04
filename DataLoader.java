package com.quickbite.config;

import com.quickbite.model.MenuItem;
import com.quickbite.model.Restaurant;
import com.quickbite.repository.RestaurantRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;
import java.util.List;

/**
 * Seeds restaurants + menus into MySQL on first start.
 * Photos are looked up by file name: /images/restaurants/<slug>.jpg and /images/food/<slug>.jpg
 * (see src/main/resources/static/images/IMAGES_NEEDED.txt). Users, orders and contact messages are never touched.
 */
@Component
public class DataLoader implements CommandLineRunner {
    private final RestaurantRepository repo;
    public DataLoader(RestaurantRepository repo) { this.repo = repo; }

    @Override
    public void run(String... args) {
        List<Restaurant> existing = repo.findAll();
        if (!existing.isEmpty()) {
            if (existing.get(0).getCategory() != null) return;   // already on the new schema
            repo.deleteAll();                                      // old demo data -> replace with new catalogue
        }

        Restaurant r1 = rest("Dosa Corner", "South Indian", "Dosa · Idli · Filter Coffee",
            "Authentic South Indian tiffin made fresh on the tawa.", 25, 4.7, 1240);
        add(r1, "Dosa", "Masala Dosa", "Crispy golden dosa filled with spiced potato, served with chutneys and sambar.", 90, 110, true, 4.7, 612);
        add(r1, "Dosa", "Plain Dosa", "Thin, crisp plain dosa with coconut chutney and sambar.", 70, 0, true, 4.4, 188);
        add(r1, "Dosa", "Ghee Roast Dosa", "Paper-thin dosa roasted in pure ghee until deep golden.", 110, 130, true, 4.6, 254);
        add(r1, "Dosa", "Onion Dosa", "Crisp dosa topped with caramelised onions and green chillies.", 95, 0, true, 4.5, 176);
        add(r1, "Dosa", "Paper Roast", "Extra-long, ultra-crisp paper roast served with sambar.", 120, 140, true, 4.6, 203);
        add(r1, "Tiffin", "Idli", "Three soft steamed idlis served with sambar and chutneys.", 60, 0, true, 4.6, 420);
        add(r1, "Tiffin", "Medu Vada", "Two crispy golden lentil vadas with sambar and chutney.", 70, 0, true, 4.5, 310);
        add(r1, "Tiffin", "Pongal", "Creamy ghee pongal tempered with pepper and cashews.", 80, 0, true, 4.4, 141);
        add(r1, "Tiffin", "Poori Masala", "Puffed pooris served with soft potato masala.", 90, 0, true, 4.5, 167);
        add(r1, "Meals", "South Indian Meals", "Traditional meals on a banana leaf: rice, sambar, rasam, kootu, poriyal and payasam.", 160, 180, true, 4.8, 298);
        add(r1, "Parotta", "Parotta", "Two flaky, layered parottas served with spicy salna.", 80, 0, true, 4.5, 264);
        add(r1, "Beverages", "Filter Coffee", "Strong, frothy South Indian filter coffee.", 40, 0, true, 4.7, 333);

        Restaurant r2 = rest("Burger House", "Fast Food", "Burgers · Shawarma · Fries",
            "Juicy burgers and street-style favourites, made to order.", 30, 4.5, 980);
        add(r2, "Burgers", "Classic Burger", "Grilled patty with lettuce, tomato, onion and house sauce in a toasted bun.", 149, 0, false, 4.5, 286);
        add(r2, "Burgers", "Cheese Burger", "Double cheese slice melted over a juicy patty with lettuce and pickles.", 169, 199, false, 4.6, 410);
        add(r2, "Burgers", "Chicken Burger", "Crispy fried chicken fillet with lettuce, cheese and mayo.", 179, 0, false, 4.5, 305);
        add(r2, "Burgers", "Veg Burger", "Crunchy veggie patty with lettuce, tomato and mint mayo.", 139, 0, true, 4.3, 192);
        add(r2, "Wraps", "Chicken Shawarma", "Freshly carved chicken wrapped with garlic sauce, pickles and veggies.", 120, 0, false, 4.6, 450);
        add(r2, "Sides", "French Fries", "Crispy golden fries with a pinch of peri-peri seasoning.", 89, 0, true, 4.4, 360);
        add(r2, "Beverages", "Cold Coffee", "Thick, chilled coffee blended with ice cream.", 99, 0, true, 4.4, 222);

        Restaurant r3 = rest("Biryani Palace", "North Indian", "Biryani · Kebabs · Grills",
            "Dum-cooked biryanis with aromatic spices and slow-cooked meats.", 40, 4.6, 1560);
        add(r3, "Biryani", "Chicken Biryani", "Fragrant seeraga samba rice layered with tender chicken, served with raita.", 240, 280, false, 4.8, 845);
        add(r3, "Biryani", "Mutton Biryani", "Slow-cooked mutton and basmati dum biryani with raita.", 320, 0, false, 4.7, 340);
        add(r3, "Biryani", "Veg Biryani", "Seasonal vegetables and paneer in fragrant basmati rice.", 190, 210, true, 4.4, 176);
        add(r3, "Biryani", "Egg Biryani", "Masala-coated boiled eggs layered with biryani rice.", 200, 0, false, 4.4, 158);
        add(r3, "Starters", "Chicken 65", "Spicy, crispy fried chicken tossed with curry leaves.", 180, 0, false, 4.6, 301);
        add(r3, "Grills", "Grilled Chicken", "Smoky char-grilled chicken marinated in yoghurt and spices.", 280, 320, false, 4.5, 214);

        Restaurant r4 = rest("Punjab Tadka", "North Indian", "Curries · Naan · Tandoor",
            "Rich North Indian curries and tandoor specials.", 35, 4.5, 870);
        add(r4, "Curries", "Paneer Butter Masala", "Soft paneer cubes simmered in a creamy tomato-butter gravy.", 220, 250, true, 4.7, 530);
        add(r4, "Curries", "Dal Makhani", "Slow-simmered black lentils finished with butter and cream.", 180, 0, true, 4.6, 260);
        add(r4, "Curries", "Butter Chicken", "Tandoori chicken in a velvety tomato-cream sauce.", 280, 320, false, 4.7, 520);
        add(r4, "Breads", "Butter Naan", "Soft tandoor-baked naan brushed with butter.", 50, 0, true, 4.5, 310);
        add(r4, "Breads", "Garlic Naan", "Naan topped with garlic and fresh coriander.", 60, 0, true, 4.5, 245);
        add(r4, "Rice", "Jeera Rice", "Basmati rice tempered with cumin and ghee.", 110, 0, true, 4.3, 120);
        add(r4, "Starters", "Paneer Tikka", "Char-grilled paneer marinated in spiced yoghurt.", 210, 0, true, 4.6, 238);

        Restaurant r5 = rest("Dragon Wok", "Chinese", "Fried Rice · Noodles · Manchurian",
            "Indo-Chinese wok classics tossed over a high flame.", 30, 4.4, 720);
        add(r5, "Rice", "Veg Fried Rice", "Colourful vegetable fried rice tossed in a hot wok.", 140, 0, true, 4.4, 365);
        add(r5, "Rice", "Chicken Fried Rice", "Wok-fried rice with chicken, egg and spring onion.", 170, 0, false, 4.5, 330);
        add(r5, "Noodles", "Veg Hakka Noodles", "Stir-fried noodles with crunchy vegetables.", 150, 0, true, 4.3, 201);
        add(r5, "Noodles", "Chicken Noodles", "Hakka noodles tossed with chicken and vegetables.", 180, 0, false, 4.4, 188);
        add(r5, "Starters", "Gobi Manchurian", "Crispy cauliflower in a tangy, spicy Manchurian sauce.", 150, 170, true, 4.4, 226);
        add(r5, "Starters", "Chilli Chicken", "Crispy chicken tossed with capsicum and chilli sauce.", 200, 0, false, 4.5, 247);
        add(r5, "Soups", "Veg Manchow Soup", "Spicy, hearty soup topped with crispy noodles.", 90, 0, true, 4.2, 104);

        Restaurant r6 = rest("Pizza Palace", "Pizza & Burgers", "Pizza · Pasta · Garlic Bread",
            "Hand-stretched pizzas baked with real mozzarella.", 35, 4.5, 1100);
        add(r6, "Pizzas", "Margherita Pizza", "Classic pizza with tomato sauce, mozzarella and fresh basil.", 249, 299, true, 4.6, 480);
        add(r6, "Pizzas", "Farmhouse Pizza", "Loaded with capsicum, onion, mushroom, olives and corn.", 329, 0, true, 4.5, 260);
        add(r6, "Pizzas", "Pepperoni Pizza", "Freshly baked pizza with melted cheese and spicy pepperoni.", 379, 429, false, 4.6, 295);
        add(r6, "Pasta", "Penne Alfredo", "Creamy white-sauce penne with herbs and parmesan.", 249, 0, true, 4.4, 150);
        add(r6, "Burgers", "Smash Burger", "Double smashed patties with cheese, caramelised onions and burger sauce.", 229, 0, false, 4.5, 212);
        add(r6, "Sides", "Garlic Bread", "Toasted baguette with garlic herb butter and melted cheese.", 119, 0, true, 4.4, 230);

        Restaurant r7 = rest("Sweet Crumbs", "Bakery & Desserts", "Cakes · Ice Cream · Pastries",
            "Freshly baked cakes, pastries and frozen treats.", 30, 4.7, 890);
        add(r7, "Cakes", "Chocolate Truffle Cake", "Rich dark chocolate truffle cake slice with silky ganache.", 120, 140, true, 4.8, 390);
        add(r7, "Cakes", "Red Velvet Pastry", "Red velvet sponge layered with cream cheese frosting.", 110, 0, true, 4.6, 176);
        add(r7, "Cakes", "Black Forest Cake", "Chocolate sponge with whipped cream and cherries.", 100, 0, true, 4.5, 204);
        add(r7, "Desserts", "Chocolate Brownie", "Warm, fudgy brownie with a crackly top.", 99, 0, true, 4.7, 268);
        add(r7, "Ice Cream", "Vanilla Ice Cream", "Two scoops of creamy vanilla ice cream.", 80, 0, true, 4.5, 190);
        add(r7, "Ice Cream", "Strawberry Ice Cream", "Two scoops of strawberry ice cream with real fruit pieces.", 90, 0, true, 4.5, 166);
        add(r7, "Desserts", "Gulab Jamun", "Two soft gulab jamuns soaked in cardamom syrup.", 70, 0, true, 4.6, 215);
        add(r7, "Bakery", "Butter Croissant", "Flaky, golden butter croissant baked fresh every morning.", 85, 0, true, 4.5, 142);

        Restaurant r8 = rest("Green Bowl", "Healthy Food", "Salads · Bowls · Smoothies",
            "Fresh, balanced bowls and salads for lighter eating.", 25, 4.6, 540);
        add(r8, "Salads", "Greek Salad", "Cucumber, tomato, olives and feta with oregano dressing.", 180, 0, true, 4.5, 130);
        add(r8, "Salads", "Grilled Chicken Salad", "Grilled chicken strips over crisp greens with lemon dressing.", 240, 270, false, 4.6, 152);
        add(r8, "Salads", "Sprouts Salad", "Protein-rich sprouts with onion, tomato and lemon.", 120, 0, true, 4.3, 96);
        add(r8, "Bowls", "Quinoa Veggie Bowl", "Quinoa with roasted vegetables, chickpeas and tahini.", 220, 0, true, 4.6, 174);
        add(r8, "Bowls", "Berry Smoothie Bowl", "Mixed berry smoothie bowl topped with granola and fruit.", 199, 0, true, 4.6, 121);
        add(r8, "Toast", "Avocado Toast", "Sourdough toast with smashed avocado, seeds and chilli flakes.", 170, 0, true, 4.5, 110);

        Restaurant r9 = rest("Juice Junction", "Beverages", "Fresh Juices · Shakes · Coolers",
            "Cold-pressed juices and shakes made from fresh fruit.", 20, 4.6, 760);
        add(r9, "Juices", "Fresh Orange Juice", "Freshly squeezed orange juice, no added sugar.", 90, 0, true, 4.6, 360);
        add(r9, "Juices", "Watermelon Juice", "Chilled watermelon juice with a hint of mint.", 70, 0, true, 4.5, 180);
        add(r9, "Juices", "Pomegranate Juice", "Fresh pomegranate juice, naturally sweet.", 120, 140, true, 4.6, 142);
        add(r9, "Juices", "Sweet Lime Juice", "Refreshing mosambi juice served over ice.", 80, 0, true, 4.4, 118);
        add(r9, "Shakes", "Mango Shake", "Thick mango milkshake made with ripe mango pulp.", 110, 0, true, 4.7, 380);
        add(r9, "Shakes", "Chocolate Shake", "Rich chocolate milkshake topped with whipped cream.", 119, 0, true, 4.5, 236);
        add(r9, "Coolers", "Mint Lemonade", "Zesty lemonade muddled with fresh mint.", 60, 0, true, 4.4, 160);

        repo.saveAll(List.of(r1, r2, r3, r4, r5, r6, r7, r8, r9));
    }

    private Restaurant rest(String name, String category, String cuisine, String desc, int mins, double rating, int reviews) {
        Restaurant r = new Restaurant();
        r.setName(name); r.setCategory(category); r.setCuisine(cuisine); r.setDescription(desc);
        r.setDeliveryMinutes(mins); r.setRating(rating); r.setReviewCount(reviews);
        r.setImageUrl(photoUrl(name, true));
        return r;
    }

    private void add(Restaurant r, String category, String name, String desc, int price, int original,
                     boolean veg, double rating, int reviews) {
        MenuItem m = new MenuItem();
        m.setRestaurant(r); m.setCategory(category); m.setName(name); m.setDescription(desc);
        m.setPrice(BigDecimal.valueOf(price));
        m.setOriginalPrice(original > price ? BigDecimal.valueOf(original) : null);
        m.setVeg(veg); m.setRating(rating); m.setReviewCount(reviews);
        m.setBestSeller(reviews >= 350);
        m.setImageUrl(photoUrl(name, false));
        r.getMenuItems().add(m);
    }

    /** Uses curated Unsplash food photography so restaurant and menu cards show real photos. */
    private String photoUrl(String name, boolean restaurant) {
        String n = name.toLowerCase();
        String id;
        if (n.contains("dosa") || n.contains("idli") || n.contains("vada") || n.contains("pongal") || n.contains("poori") || n.contains("parotta") || n.contains("south indian") || n.contains("tiffin")) id = "photo-1589301760014-d929f3979dbc";
        else if (n.contains("biryani") || n.contains("rice") || n.contains("jeera")) id = "photo-1563379091339-03246963d51a";
        else if (n.contains("burger")) id = "photo-1568901346375-23c9450c58cd";
        else if (n.contains("pizza")) id = "photo-1513104890138-7c749659a591";
        else if (n.contains("cake") || n.contains("brownie") || n.contains("pastry") || n.contains("ice cream") || n.contains("crumb") || n.contains("dessert") || n.contains("croissant") || n.contains("gulab")) id = "photo-1578985545062-69928b1d9587";
        else if (n.contains("juice") || n.contains("shake") || n.contains("coffee") || n.contains("lemonade")) id = "photo-1544145945-f90425340c7e";
        else if (n.contains("salad") || n.contains("bowl") || n.contains("healthy") || n.contains("avocado") || n.contains("sprouts")) id = "photo-1512621776951-a57141f2eefd";
        else if (n.contains("chinese") || n.contains("wok") || n.contains("noodle") || n.contains("manchurian") || n.contains("fried rice") || n.contains("dragon")) id = "photo-1585032226651-759b368d7246";
        else if (n.contains("naan") || n.contains("paneer") || n.contains("chicken") || n.contains("mutton") || n.contains("curry") || n.contains("punjab") || n.contains("tadka")) id = "photo-1603894584373-5ac82b2ae398";
        else if (n.contains("burger house") || n.contains("fries") || n.contains("shawarma")) id = "photo-1571091718767-18b5b1457add";
        else id = "photo-1546069901-ba9599a7e63c";
        // A reliable pizza photograph ID, kept separate because it contains a hyphenated slug.
        if (n.contains("pizza")) id = "photo-1513104890138-7c749659a591";
        return "https://images.unsplash.com/" + id + "?auto=format&fit=crop&w=" + (restaurant ? "1200" : "800") + "&h=" + (restaurant ? "800" : "800") + "&q=85";
    }

    /** "Masala Dosa" -> "masala-dosa" : the file name to use for the photo */
    private String slug(String s) {
        return s.toLowerCase().replaceAll("[^a-z0-9]+", "-").replaceAll("(^-|-$)", "");
    }
}
