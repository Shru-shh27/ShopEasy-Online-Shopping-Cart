package com.shop.dao;

import com.shop.model.Product;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/** In-memory catalog. Swap for a JDBC implementation to use a real database. */
public class ProductDAO {
    private static final Map<Integer, Product> CATALOG = new LinkedHashMap<>();

    static {
        add(1,  "Wireless Mouse",       "Ergonomic 2.4 GHz wireless mouse with silent clicks.",   "19.99",  "Peripherals", "\uD83D\uDDB1\uFE0F", 4.4);
        add(2,  "Mechanical Keyboard",  "Tactile blue-switch keyboard with RGB backlight.",       "69.50",  "Peripherals", "\u2328\uFE0F",       4.7);
        add(3,  "USB-C Hub",            "7-in-1 hub: HDMI, USB 3.0 and SD card reader.",          "34.00",  "Accessories", "\uD83D\uDD0C",       4.3);
        add(4,  "27-inch Monitor",      "Full HD IPS display with ultra-thin bezels.",            "189.99", "Displays",    "\uD83D\uDDA5\uFE0F", 4.6);
        add(5,  "Laptop Stand",         "Adjustable aluminium stand for 10 to 17 inch laptops.",  "29.90",  "Accessories", "\uD83D\uDCBB",       4.5);
        add(6,  "Noise-Cancelling Headphones", "Over-ear Bluetooth, 30-hour battery life.",       "129.00", "Audio",       "\uD83C\uDFA7",       4.8);
        add(7,  "HD Webcam",            "1080p webcam with built-in noise-reducing microphone.",  "45.00",  "Peripherals", "\uD83D\uDCF7",       4.2);
        add(8,  "Bluetooth Speaker",    "Waterproof portable speaker with deep bass.",            "54.99",  "Audio",       "\uD83D\uDD0A",       4.5);
        add(9,  "Portable SSD 1TB",     "Pocket-sized drive with 1000 MB/s transfer speed.",      "99.00",  "Accessories", "\uD83D\uDCBE",       4.7);
        add(10, "Gaming Mouse Pad",     "Extended non-slip desk mat with stitched edges.",        "12.50",  "Accessories", "\uD83C\uDFAE",       4.1);
        add(11, "32-inch Curved Monitor","QHD curved screen, 144 Hz, immersive viewing.",          "249.00", "Displays",    "\uD83D\uDCFA",       4.6);
        add(12, "True Wireless Earbuds","Compact earbuds with charging case and touch controls.", "39.99",  "Audio",       "\uD83C\uDFB5",       4.3);
    }

    private static void add(int id, String name, String desc, String price,
                            String category, String icon, double rating) {
        CATALOG.put(id, new Product(id, name, desc, new BigDecimal(price), category, icon, rating));
    }

    public List<Product> findAll() { return new ArrayList<>(CATALOG.values()); }

    public Product findById(int id) { return CATALOG.get(id); }

    /** Filters by text (name/description) and category. Blank values mean "no filter". */
    public List<Product> search(String query, String category) {
        String q = query == null ? "" : query.trim().toLowerCase();
        String c = category == null ? "" : category.trim();
        return CATALOG.values().stream()
                .filter(p -> c.isEmpty() || p.getCategory().equalsIgnoreCase(c))
                .filter(p -> q.isEmpty()
                        || p.getName().toLowerCase().contains(q)
                        || p.getDescription().toLowerCase().contains(q))
                .collect(Collectors.toList());
    }

    public List<String> findCategories() {
        return CATALOG.values().stream().map(Product::getCategory)
                .distinct().sorted().collect(Collectors.toList());
    }
}
