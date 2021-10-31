package com.bazlur.shoppingcart.service;

import com.bazlur.shoppingcart.dto.ProductDTO;

import java.util.List;

public interface ProductService {
    List<ProductDTO> findAllProductSortedByName();
}
