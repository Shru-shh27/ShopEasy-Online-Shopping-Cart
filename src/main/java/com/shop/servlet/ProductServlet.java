package com.shop.servlet;

import com.shop.dao.ProductDAO;
import com.shop.model.Cart;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/products")
public class ProductServlet extends HttpServlet {
    private final ProductDAO dao = new ProductDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Cart.from(req.getSession());
        String q = req.getParameter("q");
        String category = req.getParameter("category");

        req.setAttribute("products", dao.search(q, category));
        req.setAttribute("categories", dao.findCategories());
        req.setAttribute("q", q == null ? "" : q);
        req.setAttribute("selectedCategory", category == null ? "" : category);
        req.getRequestDispatcher("/WEB-INF/views/products.jsp").forward(req, resp);
    }
}
