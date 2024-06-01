package com.sample.ecommerceapplication.service;
import com.sample.ecommerceapplication.model.Product;
import java.util.List;

public interface ProductService {
    Product getSingleProduct(Long productId);
    List<Product> getAllProducts();
    Product createProduct(Product product);
}