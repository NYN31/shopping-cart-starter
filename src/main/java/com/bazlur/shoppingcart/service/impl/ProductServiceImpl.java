package com.bazlur.shoppingcart.service.impl;

import com.bazlur.shoppingcart.domain.Product;
import com.bazlur.shoppingcart.dto.ProductDTO;
import com.bazlur.shoppingcart.repository.ProductRepository;
import com.bazlur.shoppingcart.service.ProductService;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ProductServiceImpl implements ProductService {
    private ProductRepository productRepository;
    public ProductServiceImpl(ProductRepository productRepository){
        this.productRepository = productRepository;
    }

    @Override
    public List<ProductDTO> findAllProductSortedByName(){
        return productRepository.findAllProducts()
                .stream()
                .map(this::convertToDTO)
                .sorted(Comparator.comparing(ProductDTO::getName))
                .collect(Collectors.toList());
    }

    private ProductDTO convertToDTO(Product product){
        return new ProductDTO(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice()
        );
    }
}
