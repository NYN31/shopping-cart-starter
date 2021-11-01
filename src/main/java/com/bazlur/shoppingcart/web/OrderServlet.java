package com.bazlur.shoppingcart.web;

import com.bazlur.shoppingcart.dto.ShippingAddressDTO;
import com.bazlur.shoppingcart.repository.*;
import com.bazlur.shoppingcart.service.CartService;
import com.bazlur.shoppingcart.service.OrderService;
import com.bazlur.shoppingcart.service.impl.CartServiceImpl;
import com.bazlur.shoppingcart.service.impl.OrderServiceImpl;
import com.bazlur.shoppingcart.utility.security.SecurityContext;
import com.bazlur.shoppingcart.utility.validation.ValidationUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/order")
public class OrderServlet extends HttpServlet {
    private CartService cartService =
            new CartServiceImpl(new CartRepository(),
                    new ProductRepository(),
                    new CartItemRepository());

    private OrderService orderService =
            new OrderServiceImpl(new OrderRepository(),
                    new ShippingAddressRepository(),
                    new CartRepository());

    @Override
    protected void doGet(HttpServletRequest req,
                         HttpServletResponse res)
        throws ServletException, IOException{

        final String orderSuccess = req.getParameter("orderSuccess");
        if(orderSuccess != null && Boolean.parseBoolean(orderSuccess)){
            req.setAttribute("message",
                    "<strong>Congratulation!</strong>" +
                            "Your order has been placed successfully. ");
        }

        addCartToUi(req);
        req.setAttribute("countries", getCountries());
        req.getRequestDispatcher("/WEB-INF/order.jsp")
                .forward(req, res);
    }

    private void addCartToUi(HttpServletRequest req){
        if(SecurityContext.isAuthenticated(req)){
            var currentUser = SecurityContext.getCurrentUser(req);
            var cart = cartService.getCartByUser(currentUser);
            req.setAttribute("cart", cart);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req,
                          HttpServletResponse res)
        throws ServletException, IOException{

        var shippingAddress = copyParametersTo(req);
        var errors = ValidationUtil.getInstance().validate(shippingAddress);

        if(!errors.isEmpty()){
            req.setAttribute("countries", getCountries());
            req.setAttribute("errors", errors);
            req.setAttribute("shippingAddress", shippingAddress);
            addCartToUi(req);
            req.getRequestDispatcher("/WEB-INF/order.jsp")
                    .forward(req, res);
        } else{
            orderService.processOrder(shippingAddress,
                    SecurityContext.getCurrentUser(req));
            res.sendRedirect("/home?orderSuccess=true");
        }
    }

    private ShippingAddressDTO copyParametersTo(HttpServletRequest req){
        var shippingAddress = new ShippingAddressDTO();

        shippingAddress.setAddress(req.getParameter("address"));
        shippingAddress.setAddress2(req.getParameter("address2"));
        shippingAddress.setState(req.getParameter("state"));
        shippingAddress.setCountry(req.getParameter("country"));
        shippingAddress.setMobileNumber(req.getParameter("mobileNumber"));
        shippingAddress.setZip(req.getParameter("zip"));

        return shippingAddress;
    }

    private List<String> getCountries() {
        return List.of("Bangladesh", "Pakistan", "Switzerland", "Japan", "USA", "Canada");
    }
}
