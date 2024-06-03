package com.sample.ecommerceapplication.controller;

import com.sample.ecommerceapplication.exception.CategoryNotFoundException;
import com.sample.ecommerceapplication.model.Category;
import com.sample.ecommerceapplication.model.Product;
import com.sample.ecommerceapplication.service.CategoryService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/products/categories")
    public List<String> getAllCategories() throws CategoryNotFoundException {
        return categoryService.getAllCategories();
    }

    @GetMapping("/products/category/{category}")
    public List<Product> getProductsByCategory(@PathVariable String category) throws CategoryNotFoundException {
        return categoryService.getProductsByCategory(category);
    }
}
