package com.bazlur.shoppingcart.service;

import com.bazlur.shoppingcart.domain.Cart;
import com.bazlur.shoppingcart.domain.User;

public interface CartService {
    Cart getCartByUser(User currentUser);
    void addProductToCart(String productId, Cart cart);
    void removeProductToCart(String productId, Cart cart);
}
