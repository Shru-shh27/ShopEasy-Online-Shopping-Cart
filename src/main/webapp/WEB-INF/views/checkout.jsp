<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en"><head>
  <meta charset="UTF-8"><meta name="viewport" content="width=device-width, initial-scale=1">
  <title>ShopEasy - Checkout</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head><body>
<%@ include file="header.jspf" %>
<main class="container">
  <div class="steps"><span class="step done">1. Cart</span><span class="step active">2. Checkout</span><span class="step">3. Confirmation</span></div>
  <h1 class="page-title">Checkout</h1>

  <c:if test="${not empty error}"><div class="error">&#9888; <c:out value="${error}"/></div></c:if>

  <div class="two-col">
    <form method="post" action="${ctx}/checkout" class="panel form">
      <h3>Shipping details</h3>
      <label>Full name
        <input type="text" name="name" value="<c:out value='${name}'/>" placeholder="e.g. Shruti Shreya" required></label>
      <label>Email
        <input type="email" name="email" value="<c:out value='${email}'/>" placeholder="you@example.com" required></label>
      <label>Shipping address
        <textarea name="address" rows="3" placeholder="House no, street, city, PIN" required><c:out value="${address}"/></textarea></label>

      <h3>Payment method</h3>
      <div class="pay-options">
        <label class="pay"><input type="radio" name="payment" value="Cash on Delivery" checked> &#128181; Cash on Delivery</label>
        <label class="pay"><input type="radio" name="payment" value="UPI"> &#128241; UPI</label>
        <label class="pay"><input type="radio" name="payment" value="Credit / Debit Card"> &#128179; Card</label>
      </div>
      <button type="submit" class="block">Place order</button>
    </form>

    <aside class="panel summary">
      <h3>Your order</h3>
      <c:forEach var="item" items="${cart.items}">
        <div class="row small"><span><c:out value="${item.product.icon}"/> <c:out value="${item.product.name}"/> &times; <c:out value="${item.quantity}"/></span>
          <span><fmt:formatNumber value="${item.subtotal}" type="currency" currencySymbol="${cur}"/></span></div>
      </c:forEach>
      <hr>
      <div class="row"><span>Subtotal</span><span><fmt:formatNumber value="${cart.subtotal}" type="currency" currencySymbol="${cur}"/></span></div>
      <div class="row"><span>Shipping</span><span>
        <c:choose>
          <c:when test="${cart.shipping.signum() == 0}"><span class="free">FREE</span></c:when>
          <c:otherwise><fmt:formatNumber value="${cart.shipping}" type="currency" currencySymbol="${cur}"/></c:otherwise>
        </c:choose></span></div>
      <div class="row"><span>Tax (5%)</span><span><fmt:formatNumber value="${cart.tax}" type="currency" currencySymbol="${cur}"/></span></div>
      <div class="row total"><span>Total</span><span><fmt:formatNumber value="${cart.total}" type="currency" currencySymbol="${cur}"/></span></div>
      <a class="btn secondary block" href="${ctx}/cart">&larr; Edit cart</a>
    </aside>
  </div>
</main>
<%@ include file="footer.jspf" %>
</body></html>
