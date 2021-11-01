<%--
  Created by IntelliJ IDEA.
  User: noyon_admin
  Date: 10/21/21
  Time: 8:43 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="sec" uri="http://bazlur.com/functions" %>
<%@include file="includes/header.jsp" %>
<%@include file="includes/navigation.jsp"%>

    <div class="container">
        <div class="jumbotron">
            <div class="row">
                <c:if test="${message != null}">
                    <div class="alert alert-success">
                        ${message}
                    </div>
                </c:if>
            </div>
            <div class="row">
                <div class="col-6">
                    <c:if test="${sec:isAuthenticated(pageContext.request)}">
                        <h1>Hello <c:out
                                value="${sec:getCurrentUser(pageContext.request).firstName}" />
                            Welcome to e-shoppers!
                        </h1>
                    </c:if>

                    <img src="<c:url value="/image/eshoppers.png"/>" style="height: 200px" alt=""/>
                </div>
                <div class="col-4 mb-4">
                    <c:if test="${cart != null && cart.cartItems.size() > 0}">
                        <div class="cart shadow-sm p-3 mb-5 bg-white">
                            <div class="card-header">Your Cart</div>
                            <div class="card-body">
                                <p>
                                    Total Item:
                                    <span class="badge badge-pill badge-success">
                                        <c:out value="${cart.totalItem}"/>
                                    </span>
                                </p>
                                <p>Total Price: $
                                    <c:out value="${cart.totalPrice}"/>
                                </p>
                                <p>
                                    <a class="btn btn-outline-info"
                                    href="<c:url value="/checkout"/>">
                                        Checkout
                                    </a>
                                </p>
                            </div>
                        </div>
                    </c:if>
                </div>
            </div>
        </div>


        <div class="row">
            <c:forEach var="product" items="${products}">
                <div class="col-sm-4">
                    <div class="card h-100 mb-4">
                        <div class="card-body">
                            <h5 class="card-title">
                                <c:out value="${product.name}" />
                            </h5>
                            <p class="card-text">
                                <c:out value="${product.description}" />
                            </p>
                            <p class="card-text">
                                <c:out value="${product.price}" />
                            </p>
                            <a href="#" class="card-link btn btn-outline-info"
                                onclick="addToCart(${product.id})">
                                Add to cart
                            </a>
                            <form style="visibility: hidden"
                                id="addToCart_${product.id}"
                                method="post"
                                action="<c:url value="/add-to-cart?productId=${product.id}"/>">
                            </form>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </div>
    </div>

<%@include file="includes/footer.jsp" %>

<script>
    function addToCart(productId){
        let form = document.getElementById(
            "addToCart_" + productId
        );
        form.submit();
    }
</script>
