package com.shop.model;

import jakarta.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

public class Cart {
    private static final int MAX_QTY = 99;
    public static final BigDecimal FREE_SHIPPING_THRESHOLD = new BigDecimal("100.00");
    private static final BigDecimal SHIPPING_FEE = new BigDecimal("5.00");
    private static final BigDecimal TAX_RATE = new BigDecimal("0.05");   // 5 %

    private final Map<Integer, CartItem> items = new LinkedHashMap<>();

    /** Returns the session's cart, creating it on first use. */
    public static Cart from(HttpSession session) {
        Cart cart = (Cart) session.getAttribute("cart");
        if (cart == null) {
            cart = new Cart();
            session.setAttribute("cart", cart);
        }
        return cart;
    }

    public void add(Product p, int qty) {
        CartItem existing = items.get(p.getId());
        if (existing == null) {
            items.put(p.getId(), new CartItem(p, Math.min(qty, MAX_QTY)));
        } else {
            existing.setQuantity(Math.min(existing.getQuantity() + qty, MAX_QTY));
        }
    }

    /** Sets quantity; a quantity of 0 or less removes the item. */
    public void update(int productId, int qty) {
        if (qty <= 0) { items.remove(productId); return; }
        CartItem item = items.get(productId);
        if (item != null) item.setQuantity(Math.min(qty, MAX_QTY));
    }

    public void remove(int productId) { items.remove(productId); }
    public void clear() { items.clear(); }

    public Collection<CartItem> getItems() { return items.values(); }
    public boolean isEmpty() { return items.isEmpty(); }

    public int getItemCount() {
        return items.values().stream().mapToInt(CartItem::getQuantity).sum();
    }

    /** Sum of all line items (before shipping and tax). */
    public BigDecimal getSubtotal() {
        return items.values().stream()
                .map(CartItem::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    /** Free shipping on orders of 100.00 or more. */
    public BigDecimal getShipping() {
        if (items.isEmpty() || getSubtotal().compareTo(FREE_SHIPPING_THRESHOLD) >= 0) {
            return BigDecimal.ZERO.setScale(2);
        }
        return SHIPPING_FEE;
    }

    public BigDecimal getTax() {
        return getSubtotal().multiply(TAX_RATE).setScale(2, RoundingMode.HALF_UP);
    }

    /** Grand total = subtotal + shipping + tax. */
    public BigDecimal getTotal() {
        return getSubtotal().add(getShipping()).add(getTax());
    }

    public BigDecimal getAmountForFreeShipping() {
        BigDecimal left = FREE_SHIPPING_THRESHOLD.subtract(getSubtotal());
        return left.signum() < 0 ? BigDecimal.ZERO : left;
    }

    /** 0-100, used by the progress bar in the cart page. */
    public int getShippingProgress() {
        int pct = getSubtotal().multiply(BigDecimal.valueOf(100))
                .divide(FREE_SHIPPING_THRESHOLD, 0, RoundingMode.DOWN).intValue();
        return Math.min(pct, 100);
    }
}
