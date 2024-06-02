package com.sample.ecommerceapplication.service;

import com.sample.ecommerceapplication.model.Product;
import java.util.List;

public interface ProductService {
    Product createProduct(Product product);
    Product getSingleProduct(Long productId);
    List<Product> getAllProducts();
    void deleteProduct(Long productId);
    Product updateProduct(Long productId, Product product);
}