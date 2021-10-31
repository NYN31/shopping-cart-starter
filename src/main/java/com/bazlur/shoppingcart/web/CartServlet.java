package com.bazlur.shoppingcart.web;

import com.bazlur.shoppingcart.domain.Cart;
import com.bazlur.shoppingcart.domain.User;
import com.bazlur.shoppingcart.enums.Action;
import com.bazlur.shoppingcart.repository.CartItemRepository;
import com.bazlur.shoppingcart.repository.CartRepository;
import com.bazlur.shoppingcart.repository.ProductRepository;
import com.bazlur.shoppingcart.service.CartService;
import com.bazlur.shoppingcart.service.impl.CartServiceImpl;
import com.bazlur.shoppingcart.utility.security.SecurityContext;
import com.bazlur.shoppingcart.utility.validation.StringUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/add-to-cart")
public class CartServlet extends HttpServlet {
    private CartService cartService =
            new CartServiceImpl(new CartRepository(),
                    new ProductRepository(),
                    new CartItemRepository());

    @Override
    protected void doPost(HttpServletRequest req,
                          HttpServletResponse res)
        throws ServletException, IOException{

        var productId = req.getParameter("productId");
        var action = req.getParameter("action");
        var cart = getCart(req);

        if(StringUtil.isNotEmpty(action))  {
            System.out.println("util");
            processCart(productId, action, cart);
            res.sendRedirect("/checkout");
            return;
        }
        cartService.addProductToCart(productId, cart);
        res.sendRedirect("/home");
    }

    private Cart getCart(HttpServletRequest req){
        final User currentUser = SecurityContext.getCurrentUser(req);
        return cartService.getCartByUser(currentUser);
    }

    private void processCart(String productId, String action, Cart cart){
        switch(Action.valueOf(action.toUpperCase())){
            case ADD:
                cartService.addProductToCart(productId, cart);
                break;
            case REMOVE:
                cartService.removeProductToCart(productId, cart);
                break;
        }
    }
}
