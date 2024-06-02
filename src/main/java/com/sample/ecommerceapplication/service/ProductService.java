package com.sample.ecommerceapplication.service;

import com.sample.ecommerceapplication.model.Product;
import java.util.List;

public interface ProductService {
    Product getSingleProduct(Long productId);
    Product createProduct(Product product);
}