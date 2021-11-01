package com.bazlur.shoppingcart.service.impl;

import com.bazlur.shoppingcart.domain.Order;
import com.bazlur.shoppingcart.domain.ShippingAddress;
import com.bazlur.shoppingcart.domain.User;
import com.bazlur.shoppingcart.dto.ShippingAddressDTO;
import com.bazlur.shoppingcart.exception.CartItemNotFoundException;
import com.bazlur.shoppingcart.repository.CartRepository;
import com.bazlur.shoppingcart.repository.OrderRepository;
import com.bazlur.shoppingcart.repository.ShippingAddressRepository;
import com.bazlur.shoppingcart.service.OrderService;

public class OrderServiceImpl implements OrderService {
    private OrderRepository orderRepository;
    private ShippingAddressRepository shippingAddressRepository;
    private CartRepository cartRepository;

    public OrderServiceImpl(OrderRepository orderRepository,
                            ShippingAddressRepository shippingAddressRepository,
                            CartRepository cartRepository){
        this.orderRepository = orderRepository;
        this.shippingAddressRepository = shippingAddressRepository;
        this.cartRepository = cartRepository;
    }
    @Override
    public void processOrder(ShippingAddressDTO shippingAddressDTO,
                             User currentUser){
        var shippingAddress = convertTo(shippingAddressDTO);
        var savedShippingAddress
                = shippingAddressRepository.save(shippingAddress);

        var cart = cartRepository.findByUser(currentUser)
                .orElseThrow(() ->
                        new CartItemNotFoundException(
                                "Cart not found by current user"
                        ));

        Order order = new Order();
        order.setCart(cart);
        order.setShippingAddress(savedShippingAddress);
        order.setShipped(false);
        order.setUser(currentUser);

        orderRepository.save(order);
    }

    private ShippingAddress convertTo(ShippingAddressDTO shippingAddressDTO){
        var shippingAddress = new ShippingAddress();
        shippingAddress.setAddress(shippingAddressDTO.getAddress());
        shippingAddress.setAddress2(shippingAddressDTO.getAddress2());
        shippingAddress.setCountry(shippingAddressDTO.getCountry());
        shippingAddress.setState(shippingAddressDTO.getState());
        shippingAddress.setZip(shippingAddressDTO.getZip());
        shippingAddress.setMobileNumber(shippingAddressDTO.getMobileNumber());

        return shippingAddress;
    }
}
