package com.shop.servlet;

import com.shop.model.Cart;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.UUID;
import java.util.regex.Pattern;

@WebServlet("/checkout")
public class CheckoutServlet extends HttpServlet {
    private static final Pattern EMAIL = Pattern.compile("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$");

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        if (Cart.from(req.getSession()).isEmpty()) {
            resp.sendRedirect(req.getContextPath() + "/cart");
            return;
        }
        req.getRequestDispatcher("/WEB-INF/views/checkout.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        Cart cart = Cart.from(req.getSession());
        if (cart.isEmpty()) {
            resp.sendRedirect(req.getContextPath() + "/cart");
            return;
        }

        String name = trim(req.getParameter("name"));
        String email = trim(req.getParameter("email"));
        String address = trim(req.getParameter("address"));
        String payment = trim(req.getParameter("payment"));
        if (payment.isEmpty()) payment = "Cash on Delivery";

        String error = null;
        if (name.isEmpty() || address.isEmpty()) error = "Name and address are required.";
        else if (!EMAIL.matcher(email).matches()) error = "Please enter a valid email address.";

        if (error != null) {
            req.setAttribute("error", error);
            req.setAttribute("name", name);
            req.setAttribute("email", email);
            req.setAttribute("address", address);
            req.setAttribute("payment", payment);
            req.getRequestDispatcher("/WEB-INF/views/checkout.jsp").forward(req, resp);
            return;
        }

        // Copy the order details BEFORE clearing the cart (persist to a DB here in a real app).
        req.setAttribute("orderItems", new ArrayList<>(cart.getItems()));
        req.setAttribute("subtotal", cart.getSubtotal());
        req.setAttribute("shipping", cart.getShipping());
        req.setAttribute("tax", cart.getTax());
        req.setAttribute("total", cart.getTotal());
        req.setAttribute("orderId", UUID.randomUUID().toString().substring(0, 8).toUpperCase());
        req.setAttribute("orderDate",
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd MMM yyyy, hh:mm a")));
        req.setAttribute("name", name);
        req.setAttribute("email", email);
        req.setAttribute("address", address);
        req.setAttribute("payment", payment);

        cart.clear();
        req.getRequestDispatcher("/WEB-INF/views/confirmation.jsp").forward(req, resp);
    }

    private static String trim(String s) { return s == null ? "" : s.trim(); }
}
