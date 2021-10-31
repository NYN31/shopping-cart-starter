package com.bazlur.shoppingcart.web;

import com.bazlur.shoppingcart.domain.Cart;
import com.bazlur.shoppingcart.dto.ProductDTO;
import com.bazlur.shoppingcart.repository.CartItemRepository;
import com.bazlur.shoppingcart.repository.CartRepository;
import com.bazlur.shoppingcart.repository.ProductRepository;
import com.bazlur.shoppingcart.service.CartService;
import com.bazlur.shoppingcart.service.ProductService;
import com.bazlur.shoppingcart.service.impl.CartServiceImpl;
import com.bazlur.shoppingcart.service.impl.ProductServiceImpl;
import com.bazlur.shoppingcart.utility.security.SecurityContext;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/home")
public class HomeServlet extends HttpServlet {
    private ProductService productService = new ProductServiceImpl(new ProductRepository());
    private CartService cartService =
            new CartServiceImpl(new CartRepository(),
                    new ProductRepository(),
                    new CartItemRepository());

    @Override
    protected void doGet(HttpServletRequest req,
                         HttpServletResponse res)
            throws ServletException, IOException{

        List<ProductDTO> allProducts = this.productService.findAllProductSortedByName();

        if(SecurityContext.isAuthenticated(req)){
            var currentUser = SecurityContext.getCurrentUser(req);
            var cart = cartService.getCartByUser(currentUser);
            req.setAttribute("cart", cart);
        }
        req.setAttribute("products", allProducts);
        req.getRequestDispatcher("/WEB-INF/home.jsp")
                .forward(req, res);
    }
}
