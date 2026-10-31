<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en"><head>
  <meta charset="UTF-8"><meta name="viewport" content="width=device-width, initial-scale=1">
  <title>ShopEasy - Order Confirmed</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head><body>
<%@ include file="header.jspf" %>
<main class="container narrow">
  <div class="steps"><span class="step done">1. Cart</span><span class="step done">2. Checkout</span><span class="step active">3. Confirmation</span></div>

  <div class="success"><div class="check">&#10004;</div>
    <h1>Thank you, <c:out value="${name}"/>!</h1>
    <p>Your order has been placed successfully.</p>
  </div>

  <div class="panel receipt">
    <div class="meta">
      <div><span class="muted">Order ID</span><strong>#<c:out value="${orderId}"/></strong></div>
      <div><span class="muted">Date</span><strong><c:out value="${orderDate}"/></strong></div>
      <div><span class="muted">Payment</span><strong><c:out value="${payment}"/></strong></div>
    </div>
    <p class="muted">Confirmation sent to <strong><c:out value="${email}"/></strong><br>
       Delivering to: <c:out value="${address}"/></p>
    <hr>
    <c:forEach var="item" items="${orderItems}">
      <div class="row"><span><c:out value="${item.product.icon}"/> <c:out value="${item.product.name}"/> &times; <c:out value="${item.quantity}"/></span>
        <span><fmt:formatNumber value="${item.subtotal}" type="currency" currencySymbol="${cur}"/></span></div>
    </c:forEach>
    <hr>
    <div class="row"><span>Subtotal</span><span><fmt:formatNumber value="${subtotal}" type="currency" currencySymbol="${cur}"/></span></div>
    <div class="row"><span>Shipping</span><span>
      <c:choose>
        <c:when test="${shipping.signum() == 0}"><span class="free">FREE</span></c:when>
        <c:otherwise><fmt:formatNumber value="${shipping}" type="currency" currencySymbol="${cur}"/></c:otherwise>
      </c:choose></span></div>
    <div class="row"><span>Tax (5%)</span><span><fmt:formatNumber value="${tax}" type="currency" currencySymbol="${cur}"/></span></div>
    <div class="row total"><span>Total paid</span><span><fmt:formatNumber value="${total}" type="currency" currencySymbol="${cur}"/></span></div>
  </div>
  <p class="center"><a class="btn" href="${ctx}/products">Continue shopping</a></p>
</main>
<%@ include file="footer.jspf" %>
</body></html>
