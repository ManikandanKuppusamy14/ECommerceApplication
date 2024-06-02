package com.sample.ecommerceapplication.service;

import com.sample.ecommerceapplication.dto.FakeStoreCategoryDto;
import com.sample.ecommerceapplication.exception.CategoryNotFoundException;
import com.sample.ecommerceapplication.model.Category;
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
    public List<Category> getAllCategories() throws CategoryNotFoundException {

        List<Category> categories = new ArrayList<>();

        // Fetch the array of FakeStoreCategoryDto from the API
        FakeStoreCategoryDto[] res = restTemplate.getForObject(
                "https://fakestoreapi.com/products/categories",
                FakeStoreCategoryDto[].class
        );

        // Check if the response is null or empty
        if (res == null || res.length == 0) {
            throw new CategoryNotFoundException("No categories found");
        }

        // Convert each FakeStoreCategoryDto to a Product and add to the list
        for (FakeStoreCategoryDto fs : res) {
            categories.add(fs.toCategory());
        }

        return categories;
    }
}
