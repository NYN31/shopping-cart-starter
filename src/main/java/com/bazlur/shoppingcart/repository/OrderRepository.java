package com.bazlur.shoppingcart.repository;

import com.bazlur.shoppingcart.domain.Order;

import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

public class OrderRepository {
    private static final Set<Order> ORDERS
            = new CopyOnWriteArraySet<>();

    public Order save(Order order){
        ORDERS.add(order);
        return order;
    }
}
