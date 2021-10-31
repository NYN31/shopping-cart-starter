<%--
  Created by IntelliJ IDEA.
  User: noyon_admin
  Date: 10/31/21
  Time: 12:27 AM
  To change this template use File | Settings | File Templates.
--%>
<%@include file="includes/header.jsp" %>
<%@include file="includes/navigation.jsp" %>

<div class="container">
    <h3>Your Cart</h3>
    <div class="row">
        <table class="table table-hover">
            <thead>
                <tr>
                    <th class="w-50">Name - Description</th>
                    <th>Quantity</th>
                    <th>Price</th>
                    <th>#</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="cartItem" items="${cart.cartItems}">
                    <tr>
                        <td>
                            <c:out  value="${cartItem.product.name}" />
                            -
                            <c:out value="${cartItem.product.description}" />
                        </td>
                        <td>
                            <div class="btn-group" role="gropu">
                                <button class="btn btn-outline-warning"
                                    onclick="doAction(${cartItem.product.getId()}, 'remove')">
                                    -
                                </button>
                                <button type="button" class="btn">
                                    <c:out value="${cartItem.quantity}" />
                                </button>
                                <button class="btn btn-outline-success"
                                    onclick="doAction(${cartItem.product.getId()}, 'add')">
                                    +
                                </button>
                            </div>
                        </td>
                        <td>
                            $ <c:out value="${cartItem.price}"/>
                        </td>
                        <td>
                            <a href="#" class="btn btn-outline-warning">Remove</a>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>

            <tfoot>
                <tr>
                    <td>
                        <h4> Subtotal
                            (<c:out value="${cart.totalItem} items"/>):
                            $<c:out value="${cart.totalPrice}"/>
                        </h4>
                    </td>
                    <td></td>
                    <td>
                        <a href="<c:url value="/" />"
                           class="btn btn-outline-info text-left">
                            Continue to Shopping
                        </a>
                    </td>
                    <td>
                        <a href="<c:url value="#"/>"
                           class="btn btn-outline-success text-right">
                            Proceed to Checkout
                        </a>
                    </td>
                </tr>
            </tfoot>
        </table>
    </div>
</div>

<script>
    function doAction(productId, action){
        fetch("/add-to-cart", {
            method: 'POST',
            headers: {
                "content-type": "application/x-www-form-urlencoded; charset=UTF-8"
            },
            body: "productId=" + productId + "&action=" + action
        }).then((response) =>{
            if(response.redirected){
                window.location.replace(response.url);
            }
        });
    }
</script>

<%@include file="includes/footer.jsp" %>
