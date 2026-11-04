package com.quickbite.service;

import com.quickbite.model.MenuItem;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;
import java.math.BigDecimal;
import java.util.*;

/** One cart per browser session. */
@Component
@SessionScope
public class Cart {

    public static class Line {
        private final Long itemId;
        private final String name;
        private final BigDecimal price;
        private final String imageUrl;
        private int qty;

        Line(Long itemId, String name, BigDecimal price, String imageUrl) {
            this.itemId = itemId; this.name = name; this.price = price; this.imageUrl = imageUrl;
        }
        public Long getItemId() { return itemId; }
        public String getName() { return name; }
        public BigDecimal getPrice() { return price; }
        public String getImageUrl() { return imageUrl; }
        public int getQty() { return qty; }
        public BigDecimal getSubtotal() { return price.multiply(BigDecimal.valueOf(qty)); }
    }

    private final Map<Long, Line> lines = new LinkedHashMap<>();

    public void add(MenuItem m) {
        lines.computeIfAbsent(m.getId(), id -> new Line(id, m.getName(), m.getPrice(), m.getImageUrl())).qty++;
    }

    public void decrease(Long id) {
        Line l = lines.get(id);
        if (l != null && --l.qty <= 0) lines.remove(id);
    }

    public void remove(Long id) { lines.remove(id); }
    public void clear() { lines.clear(); }
    public List<Line> getLines() { return new ArrayList<>(lines.values()); }
    public int getCount() { return lines.values().stream().mapToInt(Line::getQty).sum(); }
    public BigDecimal getTotal() {
        return lines.values().stream().map(Line::getSubtotal).reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
