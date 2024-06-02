package com.sample.ecommerceapplication.service;

import com.sample.ecommerceapplication.exception.ProductNotFoundException;
import com.sample.ecommerceapplication.model.Product;
import java.util.List;

public interface ProductService {
    Product getSingleProduct(Long productId) throws ProductNotFoundException;
    Product createProduct(Product product) throws ProductNotFoundException ;
    List<Product> getAllProducts() throws ProductNotFoundException;
    void deleteProduct(Long productId) throws ProductNotFoundException;
    Product updateProduct(Long productId, Product product) throws ProductNotFoundException;
}