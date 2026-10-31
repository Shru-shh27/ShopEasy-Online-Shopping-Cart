# 🛍️ ShopEasy – Online Shopping Cart

A simple, user-friendly e-commerce web application with full shopping cart functionality, built using **Java, Servlets, and JSP** following the **MVC** pattern.

![Java](https://img.shields.io/badge/Java-17%2B-orange)
![Servlets](https://img.shields.io/badge/Jakarta-Servlets%206-blue)
![JSP](https://img.shields.io/badge/JSP-JSTL-green)
![Maven](https://img.shields.io/badge/Build-Maven-red)
![Tomcat](https://img.shields.io/badge/Server-Tomcat%2010.1-yellow)

---

## 📌 Overview

ShopEasy lets customers browse products, search and filter by category, add items to a cart, update quantities, and place an order through a validated checkout flow. The cart is stored in the user's HTTP session, and the catalog is kept in memory, so there is no database setup needed.

## ✨ Features

- 🛒 **Shopping cart:** add, update quantity, remove items, clear cart
- 🔍 **Product search** and **category filters**
- ⭐ 12 sample products with ratings and category-themed cards
- 💰 **Order summary** with subtotal, shipping (free over 100) and 5% tax
- 🚚 Free-shipping **progress bar** in the cart
- 📝 **Checkout form** with server-side validation (name, email, address)
- 💳 Payment method selection (Cash on Delivery, UPI, Card)
- 🧾 **Order confirmation** page with receipt and order ID
- 📱 Responsive, modern UI that works on desktop and mobile
- 🔒 Output escaped with `<c:out>` to prevent XSS
- ♻️ Post-Redirect-Get pattern to avoid duplicate form submissions

## 🧰 Tech Stack

| Layer | Technology |
|-------|-----------|
| Language | Java 17+ |
| Controller | Jakarta Servlets 6.0 |
| View | JSP + JSTL |
| Build tool | Maven |
| Server | Apache Tomcat 10.1+ |
| Styling | Plain CSS (no frameworks) |
| Data | In-memory catalog (`ProductDAO`) |

## 🏗️ Project Structure

```
ShopEasyCart/
├── pom.xml
└── src/main
    ├── java/com/shop
    │   ├── model/        Product, CartItem, Cart
    │   ├── dao/          ProductDAO (in-memory catalog)
    │   └── servlet/      ProductServlet, CartServlet, CheckoutServlet
    └── webapp
        ├── index.jsp
        ├── css/style.css
        └── WEB-INF
            ├── web.xml
            └── views/    products.jsp, cart.jsp, checkout.jsp,
                          confirmation.jsp, header.jspf, footer.jspf
```

## 🔄 How It Works (MVC)

| Component | Role |
|-----------|------|
| **Model** | `Product`, `CartItem`, `Cart` hold data and cart/price logic |
| **View** | JSP pages render the UI. They live in `WEB-INF/views/` so they can't be opened directly by URL |
| **Controller** | Servlets handle requests and forward to views |

| URL | Servlet | Purpose |
|-----|---------|---------|
| `/products` | `ProductServlet` | List, search and filter products |
| `/cart` | `CartServlet` | View cart; add / update / remove / clear |
| `/checkout` | `CheckoutServlet` | Show checkout form; validate and place order |

## ⚙️ Prerequisites

- **JDK 17 or newer** (e.g. [Eclipse Temurin](https://adoptium.net))
- **Apache Maven 3.8+**
- **Apache Tomcat 10.1.x** (must be 10.1 or newer, since the project uses
