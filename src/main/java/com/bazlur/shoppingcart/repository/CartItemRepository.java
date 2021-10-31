package com.bazlur.shoppingcart.repository;

import com.bazlur.shoppingcart.domain.CartItem;

import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

public class CartItemRepository {
    private static final Set<CartItem> CARTS
            = new CopyOnWriteArraySet<>();

    public CartItem save(CartItem cartItem){
        CARTS.add(cartItem);
        return cartItem;
    }

    public CartItem update(CartItem cartItem){
        CARTS.add(cartItem);
        return cartItem;
    }

    public void remove(CartItem cartItem){
        CARTS.remove(cartItem);
    }
}
