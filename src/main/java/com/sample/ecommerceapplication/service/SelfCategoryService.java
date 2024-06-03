package com.sample.ecommerceapplication.service;

import com.sample.ecommerceapplication.exception.CategoryNotFoundException;
import com.sample.ecommerceapplication.model.Category;
import com.sample.ecommerceapplication.model.Product;
import com.sample.ecommerceapplication.repositories.CategoryRepository;
import com.sample.ecommerceapplication.repositories.ProductRepository;
import org.springframework.stereotype.Service;

import java.awt.event.ComponentAdapter;
import java.util.ArrayList;
import java.util.List;

@Service("SelfCategoryService")
public class SelfCategoryService implements CategoryService{

    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    public SelfCategoryService(CategoryRepository categoryRepository, ProductRepository productRepository) {
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
    }

    @Override
    public List<String> getAllCategories() throws CategoryNotFoundException {
        List<Category> categories = categoryRepository.findAll();

        if (categories.isEmpty()) {
            throw new CategoryNotFoundException("Categories not found");
        }

        List<String> categoriesNames = new ArrayList<>();
        for (Category category : categories) {
            categoriesNames.add(category.getTitle());
        }
        return categoriesNames;
    }

    @Override
    public List<Product> getProductsByCategory(String category) throws CategoryNotFoundException {
        Category categoryId = categoryRepository.findByTitle(category);
        if (categoryId == null) {
            throw new CategoryNotFoundException("Category not found");
        }
        return productRepository.getProductsByCategoryId(categoryId.getId());
    }
}
