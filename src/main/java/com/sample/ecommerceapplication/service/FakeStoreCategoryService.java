package com.sample.ecommerceapplication.service;

import com.sample.ecommerceapplication.dto.FakeStoreProductDto;
import com.sample.ecommerceapplication.exception.CategoryNotFoundException;
import com.sample.ecommerceapplication.model.Product;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class FakeStoreCategoryService implements CategoryService {

    private final RestTemplate restTemplate;

    public FakeStoreCategoryService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public List<String> getAllCategories() throws CategoryNotFoundException {

        // Fetch the array of category titles from the API
        String[] categories = restTemplate.getForObject(
                "https://fakestoreapi.com/products/categories",
                String[].class
        );

        // Check if the categories is null or empty
        if (categories == null || categories.length == 0) {
            throw new CategoryNotFoundException("No categories found");
        }

        List<String> categoryList = new ArrayList<>();

        for (String category : categories) {
            categoryList.add(category);
        }

        return categoryList;
    }

    @Override
    public List<Product> getProductsByCategory(String category) throws CategoryNotFoundException {

        // Fetch the array of products from the API
        FakeStoreProductDto[] response = restTemplate.getForObject(
                "https://fakestoreapi.com/products/category/" + category
                , FakeStoreProductDto[].class);

        // Check if the response is null or empty
        if (response == null || response.length == 0) {
            throw new CategoryNotFoundException("No products found");
        }

        List<Product> products = new ArrayList<>();

        // Convert each FakeStoreProductDto to a Product and add to the list
        for (FakeStoreProductDto fs : response) {
            products.add(fs.toProduct());
        }

        return products;
    }
}
