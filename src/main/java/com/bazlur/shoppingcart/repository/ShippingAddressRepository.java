package com.bazlur.shoppingcart.repository;

import com.bazlur.shoppingcart.domain.ShippingAddress;

import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

public class ShippingAddressRepository {
    private static final Set<ShippingAddress> SHIPPING_ADDRESSES
            = new CopyOnWriteArraySet<>();

    public ShippingAddress save(ShippingAddress shippingAddress){
        SHIPPING_ADDRESSES.add(shippingAddress);
        return shippingAddress;
    }
}
