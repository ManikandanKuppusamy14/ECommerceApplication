package com.sample.ecommerceapplication.service;

import com.sample.ecommerceapplication.exception.CategoryNotFoundException;
import com.sample.ecommerceapplication.model.Category;

import java.util.List;

public interface CategoryService {
    public List<Category> getAllCategories() throws CategoryNotFoundException;
}
