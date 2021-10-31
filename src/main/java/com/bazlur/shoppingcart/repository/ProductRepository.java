package com.bazlur.shoppingcart.repository;

import com.bazlur.shoppingcart.domain.Product;
import com.bazlur.shoppingcart.dto.ProductDTO;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public class ProductRepository {

    private static final List<Product> ALL_PRODUCTS = List.of(
            new Product(
                    1L,
                    "Apple iPad",
                    "Apple iPad 10.2 32GB",
                    BigDecimal.valueOf(369.99)
            ),
            new Product(
                    2L,
                    "Headphones",
                    "Jabra Elite Bluetooth Headphones",
                    BigDecimal.valueOf(249.99)
            ),
            new Product(
                    3L,
                    "Redmi 8",
                    "xiaomi communications co ltd",
                    BigDecimal.valueOf(14299)
            ),
            new Product(
                    4L,
                    "Laptop",
                    "Asus fx-503V Gaming laptop",
                    BigDecimal.valueOf(84499)
            )
    );

    public List<Product> findAllProducts(){
        return ALL_PRODUCTS;
    }

    public Optional<Product> findById(Long productId){
        return findAllProducts().stream().filter(product ->
                product.getId().equals(productId)).findFirst();
    }
}
