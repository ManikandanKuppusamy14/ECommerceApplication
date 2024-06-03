package com.sample.ecommerceapplication.service;

import com.sample.ecommerceapplication.exception.CategoryNotFoundException;
import com.sample.ecommerceapplication.model.Product;

import java.util.List;

public interface CategoryService {
    public List<String> getAllCategories() throws CategoryNotFoundException;
    List<Product> getProductsByCategory(String category) throws CategoryNotFoundException;
}
