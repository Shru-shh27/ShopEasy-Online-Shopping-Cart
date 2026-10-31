package com.shop.model;

import java.math.BigDecimal;

public class Product {
    private final int id;
    private final String name;
    private final String description;
    private final BigDecimal price;
    private final String category;
    private final String icon;
    private final double rating;

    public Product(int id, String name, String description, BigDecimal price,
                   String category, String icon, double rating) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.category = category;
        this.icon = icon;
        this.rating = rating;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public BigDecimal getPrice() { return price; }
    public String getCategory() { return category; }
    public String getIcon() { return icon; }
    public double getRating() { return rating; }

    /** Used by the JSP to pick a colour theme for the product card. */
    public String getCssClass() { return category.toLowerCase(); }
}
