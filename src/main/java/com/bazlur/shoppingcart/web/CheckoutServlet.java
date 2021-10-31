package com.bazlur.shoppingcart.web;

import com.bazlur.shoppingcart.repository.CartItemRepository;
import com.bazlur.shoppingcart.repository.CartRepository;
import com.bazlur.shoppingcart.repository.ProductRepository;
import com.bazlur.shoppingcart.service.CartService;
import com.bazlur.shoppingcart.service.impl.CartServiceImpl;
import com.bazlur.shoppingcart.utility.security.SecurityContext;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/checkout")
public class CheckoutServlet extends HttpServlet {
    private CartService cartService
            = new CartServiceImpl(new CartRepository(),
                new ProductRepository(),
                new CartItemRepository()
            );

    @Override
    protected void doGet(HttpServletRequest req,
                        HttpServletResponse res)
        throws ServletException, IOException {

        var currentUser = SecurityContext.getCurrentUser(req);
        var cart = cartService.getCartByUser(currentUser);
        req.setAttribute("cart", cart);

        req.getRequestDispatcher("/WEB-INF/checkout.jsp")
                .forward(req, res);
    }
}
