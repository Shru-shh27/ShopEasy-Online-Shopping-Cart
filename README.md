# ShopEasy – Online Shopping Cart (Java, Servlets, JSP)

## Requirements
- JDK 17+
- Maven 3.8+
- Apache Tomcat 10.1+ (Jakarta EE 10 – uses `jakarta.*` packages)

## Build & run
    mvn clean package
    cp target/shopping-cart.war $TOMCAT_HOME/webapps/
    $TOMCAT_HOME/bin/startup.sh        # startup.bat on Windows

Open http://localhost:8080/shopping-cart/

## Features
- 12 products with search, category filters and ratings
- Product catalog, add to cart, update quantity, remove items, clear cart
- Order summary with shipping (free over 100) and 5% tax
- Session-based cart with running totals (BigDecimal)
- Checkout form with server-side validation and order confirmation
- Post-Redirect-Get on cart changes; output escaped with <c:out>

## Structure
    model/    Product, CartItem, Cart
    dao/      ProductDAO (in-memory; replace with JDBC)
    servlet/  ProductServlet, CartServlet, CheckoutServlet
    WEB-INF/views/  JSP views (not directly reachable by URL)

## Ideas for next steps
Database via JDBC/DataSource, user login, order history, product search, payment gateway.

## Important (Windows)
Keep this folder in a plain English path with no spaces or special characters,
e.g. C:\projects\ShopEasyCart. Paths inside OneDrive/Documents with non-English
names can break IntelliJ's build ("InvalidPathException: Illegal char <?>").

## IntelliJ run settings (Smart Tomcat)
- Tomcat Server: Tomcat 10.1.x  (NOT Tomcat 9)
- Deployment Directory: <project folder>\src\main\webapp
- Context Path: /shopping-cart
- Open: http://localhost:8080/shopping-cart/
