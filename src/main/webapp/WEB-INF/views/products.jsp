<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en"><head>
  <meta charset="UTF-8"><meta name="viewport" content="width=device-width, initial-scale=1">
  <title>ShopEasy - Products</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head><body>
<%@ include file="header.jspf" %>

<section class="hero">
  <div class="container">
    <h1>Gear up your workspace</h1>
    <p>Quality tech accessories at friendly prices. Free shipping over
       <fmt:formatNumber value="100" type="currency" currencySymbol="${cur}"/>.</p>
    <form class="search" method="get" action="${ctx}/products">
      <input type="hidden" name="category" value="<c:out value='${selectedCategory}'/>">
      <input type="text" name="q" value="<c:out value='${q}'/>" placeholder="Search products...">
      <button type="submit">Search</button>
    </form>
  </div>
</section>

<main class="container">
  <c:if test="${param.added == '1'}"><div class="toast">&#10004; Item added to your cart</div></c:if>

  <div class="chips">
    <a class="chip ${empty selectedCategory ? 'active' : ''}" href="${ctx}/products">All</a>
    <c:forEach var="cat" items="${categories}">
      <a class="chip ${selectedCategory == cat ? 'active' : ''}"
         href="${ctx}/products?category=${cat}"><c:out value="${cat}"/></a>
    </c:forEach>
  </div>

  <p class="result-count"><c:out value="${products.size()}"/> product(s) found</p>

  <c:if test="${empty products}">
    <div class="empty"><div class="big">&#128269;</div>
      <h3>No products match your search</h3>
      <a class="btn" href="${ctx}/products">Clear filters</a></div>
  </c:if>

  <div class="grid">
    <c:forEach var="p" items="${products}">
      <article class="card">
        <div class="thumb ${p.cssClass}">
          <span class="emoji"><c:out value="${p.icon}"/></span>
          <span class="tag"><c:out value="${p.category}"/></span>
        </div>
        <div class="card-body">
          <h3><c:out value="${p.name}"/></h3>
          <div class="rating">&#9733; <fmt:formatNumber value="${p.rating}" minFractionDigits="1" maxFractionDigits="1"/></div>
          <p class="desc"><c:out value="${p.description}"/></p>
          <div class="buy">
            <span class="price"><fmt:formatNumber value="${p.price}" type="currency" currencySymbol="${cur}"/></span>
            <form method="post" action="${ctx}/cart">
              <input type="hidden" name="action" value="add">
              <input type="hidden" name="productId" value="${p.id}">
              <input type="number" name="quantity" value="1" min="1" max="99" aria-label="Quantity">
              <button type="submit">Add</button>
            </form>
          </div>
        </div>
      </article>
    </c:forEach>
  </div>
</main>
<%@ include file="footer.jspf" %>
</body></html>
