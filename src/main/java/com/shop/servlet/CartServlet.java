package com.shop.servlet;

import com.shop.dao.ProductDAO;
import com.shop.model.Cart;
import com.shop.model.Product;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/cart")
public class CartServlet extends HttpServlet {
    private final ProductDAO dao = new ProductDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Cart.from(req.getSession());
        req.getRequestDispatcher("/WEB-INF/views/cart.jsp").forward(req, resp);
    }

    /** Handles add / update / remove / clear, then redirects (Post-Redirect-Get). */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Cart cart = Cart.from(req.getSession());
        String action = req.getParameter("action");
        int id = parseInt(req.getParameter("productId"), -1);
        int qty = parseInt(req.getParameter("quantity"), 1);
        String redirect = req.getContextPath() + "/cart";

        if ("add".equals(action)) {
            Product p = dao.findById(id);
            if (p != null && qty > 0) cart.add(p, qty);
            redirect = req.getContextPath() + "/products?added=1";
        } else if ("update".equals(action)) {
            cart.update(id, qty);
        } else if ("remove".equals(action)) {
            cart.remove(id);
        } else if ("clear".equals(action)) {
            cart.clear();
        }
        resp.sendRedirect(redirect);
    }

    private static int parseInt(String s, int fallback) {
        try { return Integer.parseInt(s.trim()); }
        catch (Exception e) { return fallback; }
    }
}
