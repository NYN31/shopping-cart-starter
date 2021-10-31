package com.bazlur.shoppingcart.service.impl;

import com.bazlur.shoppingcart.domain.Cart;
import com.bazlur.shoppingcart.domain.CartItem;
import com.bazlur.shoppingcart.domain.Product;
import com.bazlur.shoppingcart.domain.User;
import com.bazlur.shoppingcart.exception.CartItemNotFoundException;
import com.bazlur.shoppingcart.exception.ProductNotFoundException;
import com.bazlur.shoppingcart.repository.CartItemRepository;
import com.bazlur.shoppingcart.repository.CartRepository;
import com.bazlur.shoppingcart.repository.ProductRepository;
import com.bazlur.shoppingcart.service.CartService;

import java.math.BigDecimal;
import java.util.Optional;

public class CartServiceImpl implements CartService {
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final CartItemRepository cartItemRepository;
    public CartServiceImpl(CartRepository cartRepository,
                           ProductRepository productRepository,
                           CartItemRepository cartItemRepository){
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
        this.cartItemRepository = cartItemRepository;
    }

    @Override
    public Cart getCartByUser(User currentUser){
        Optional<Cart> optionalCart = cartRepository.findByUser(currentUser);

        return optionalCart.orElseGet(() -> createNewCart(currentUser));
    }

    @Override
    public void addProductToCart(String productId, Cart cart){
        Product product = findProduct(productId);
        addProductToCartInService(product, cart);
        updateCart(cart);
    }

    @Override
    public void removeProductToCart(String productId, Cart cart){
        Product product = findProduct(productId);

        removeProductToCartInService(product, cart);
        updateCart(cart);
    }

    private Product findProduct(String productId){
        if(productId == null || productId.length() == 0){
            throw new IllegalArgumentException("Product id cannot be null");
        }

        Long id = parseProductId(productId);
        return productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(
                        "Product not found by id: " + id
                ));
    }

    private void updateCart(Cart cart){
        Integer totalTotalItem = getTotalItem(cart);
        BigDecimal totalPrice = calculateTotalPrice(cart);

        cart.setTotalItem(totalTotalItem);
        cart.setTotalPrice(totalPrice);

        cartRepository.update(cart);
    }

    private void addProductToCartInService(Product product, Cart cart){
        var cartItemOptional
                = findSimilarProductInCart(cart, product);
        var cartItem = cartItemOptional
                .map(this::increaseQuantityByOne)
                .orElseGet(() -> createNewShoppingCartItem(product));
        cart.getCartItems().add(cartItem);
    }

    private void removeProductToCartInService(Product productToRemove, Cart cart){
        var itemOptional = cart.getCartItems()
                .stream()
                .filter(cartItem ->
                        cartItem.getProduct().equals(productToRemove))
                .findAny();

        var cartItem = itemOptional
                .orElseThrow(()
                -> new CartItemNotFoundException(
                        "Cart not found by product: "
                        + productToRemove
                ));

        if(cartItem.getQuantity() > 1){
            cartItem.setQuantity(cartItem.getQuantity() - 1);
            cartItem.setPrice(cartItem.getPrice().subtract(
                    productToRemove.getPrice()
            ));
            cart.getCartItems().add(cartItem);
            cartItemRepository.update(cartItem);
        }else{
            cart.getCartItems().remove(cartItem);
            cartItemRepository.remove(cartItem);
        }
    }

    private CartItem createNewShoppingCartItem(Product product){
        var cartItem = new CartItem();
        cartItem.setProduct(product);
        cartItem.setQuantity(1);
        cartItem.setPrice(product.getPrice());

        return cartItemRepository.save(cartItem);
    }

    private CartItem increaseQuantityByOne(CartItem cartItem){
        cartItem.setQuantity(cartItem.getQuantity() + 1);

        BigDecimal totalPrice = cartItem.getProduct().getPrice()
                .multiply(BigDecimal.valueOf(cartItem.getQuantity()));
        cartItem.setPrice(totalPrice);
        return cartItemRepository.update(cartItem);
    }

    private Optional<CartItem> findSimilarProductInCart(Cart cart, Product product){
        return cart.getCartItems()
                .stream().filter(cartItem ->
                        cartItem.getProduct().equals(product))
                .findFirst();
    }

    private Integer getTotalItem(Cart cart){
        return cart.getCartItems()
                .stream().map(CartItem::getQuantity)
                .reduce(0, Integer::sum);
    }

    private BigDecimal calculateTotalPrice(Cart cart){
        return cart.getCartItems()
                .stream().map(CartItem::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private Cart createNewCart(User currentUser){
        Cart cart = new Cart();
        cart.setUser(currentUser);

        return cartRepository.save(cart);
    }

    private Long parseProductId(String productId){
        try{
            return Long.parseLong(productId);
        } catch (NumberFormatException ex){
            throw new IllegalArgumentException(
                    "Product id must be a number", ex
            );
        }
    }

}
