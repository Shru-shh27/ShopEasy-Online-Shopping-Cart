<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en"><head>
  <meta charset="UTF-8"><meta name="viewport" content="width=device-width, initial-scale=1">
  <title>ShopEasy - Your Cart</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head><body>
<%@ include file="header.jspf" %>
<main class="container">
  <div class="steps"><span class="step active">1. Cart</span><span class="step">2. Checkout</span><span class="step">3. Confirmation</span></div>
  <h1 class="page-title">Shopping Cart</h1>

  <c:choose>
    <c:when test="${empty cart or cart.empty}">
      <div class="empty"><div class="big">&#128722;</div>
        <h3>Your cart is empty</h3>
        <p class="muted">Looks like you haven't added anything yet.</p>
        <a class="btn" href="${ctx}/products">Start shopping</a></div>
    </c:when>
    <c:otherwise>
      <div class="two-col">
        <section>
          <div class="ship-box">
            <c:choose>
              <c:when test="${cart.amountForFreeShipping.signum() == 0}">
                <strong>&#127881; You've unlocked FREE shipping!</strong>
              </c:when>
              <c:otherwise>
                Add <strong><fmt:formatNumber value="${cart.amountForFreeShipping}" type="currency" currencySymbol="${cur}"/></strong> more for FREE shipping
              </c:otherwise>
            </c:choose>
            <div class="progress"><div class="bar" style="width:${cart.shippingProgress}%"></div></div>
          </div>

          <div class="panel">
            <c:forEach var="item" items="${cart.items}">
              <div class="line">
                <div class="mini ${item.product.cssClass}"><c:out value="${item.product.icon}"/></div>
                <div class="info">
                  <h4><c:out value="${item.product.name}"/></h4>
                  <span class="muted"><c:out value="${item.product.category}"/> &bull;
                    <fmt:formatNumber value="${item.product.price}" type="currency" currencySymbol="${cur}"/> each</span>
                </div>
                <form method="post" action="${ctx}/cart" class="qty">
                  <input type="hidden" name="action" value="update">
                  <input type="hidden" name="productId" value="${item.product.id}">
                  <input type="number" name="quantity" value="${item.quantity}" min="0" max="99"
                         onchange="this.form.submit()" aria-label="Quantity">
                </form>
                <div class="sub"><fmt:formatNumber value="${item.subtotal}" type="currency" currencySymbol="${cur}"/></div>
                <form method="post" action="${ctx}/cart">
                  <input type="hidden" name="action" value="remove">
                  <input type="hidden" name="productId" value="${item.product.id}">
                  <button type="submit" class="icon-btn" title="Remove item">&#10005;</button>
                </form>
              </div>
            </c:forEach>
          </div>

          <div class="actions">
            <a class="btn secondary" href="${ctx}/products">&larr; Continue shopping</a>
            <form method="post" action="${ctx}/cart">
              <input type="hidden" name="action" value="clear">
              <button type="submit" class="danger">Clear cart</button>
            </form>
          </div>
        </section>

        <aside class="panel summary">
          <h3>Order Summary</h3>
          <div class="row"><span>Items</span><span><c:out value="${cart.itemCount}"/></span></div>
          <div class="row"><span>Subtotal</span><span><fmt:formatNumber value="${cart.subtotal}" type="currency" currencySymbol="${cur}"/></span></div>
          <div class="row"><span>Shipping</span><span>
            <c:choose>
              <c:when test="${cart.shipping.signum() == 0}"><span class="free">FREE</span></c:when>
              <c:otherwise><fmt:formatNumber value="${cart.shipping}" type="currency" currencySymbol="${cur}"/></c:otherwise>
            </c:choose></span></div>
          <div class="row"><span>Tax (5%)</span><span><fmt:formatNumber value="${cart.tax}" type="currency" currencySymbol="${cur}"/></span></div>
          <div class="row total"><span>Total</span><span><fmt:formatNumber value="${cart.total}" type="currency" currencySymbol="${cur}"/></span></div>
          <a class="btn block" href="${ctx}/checkout">Proceed to checkout &rarr;</a>
        </aside>
      </div>
    </c:otherwise>
  </c:choose>
</main>
<%@ include file="footer.jspf" %>
</body></html>
