package com.sample.ecommerceapplication.service;

import com.sample.ecommerceapplication.dto.FakeStoreCategoryDto;
import com.sample.ecommerceapplication.exception.CategoryNotFoundException;
import com.sample.ecommerceapplication.model.Category;
import com.sample.ecommerceapplication.model.Product;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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

        // Check if the response is null or empty
        if (categories == null) {
            throw new CategoryNotFoundException("No categories found");
        }

        return Arrays.asList(categories);
    }

    @Override
    public List<Product> getProductsByCategory(String category) throws CategoryNotFoundException {

        // Fetch the array of products from the API
        Product[] products = restTemplate.getForObject(
                "https://fakestoreapi.com/products/category/" + category
                , Product[].class);

        // Convert the array to a list and return
        if (products != null) {
            return Arrays.asList(products);
        }

        return null;
    }
}
