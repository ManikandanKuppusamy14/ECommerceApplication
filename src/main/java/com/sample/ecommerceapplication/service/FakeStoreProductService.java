package com.sample.ecommerceapplication.service;

import com.sample.ecommerceapplication.dto.FakeStoreProductDto;
import com.sample.ecommerceapplication.exception.ProductNotFoundException;
import com.sample.ecommerceapplication.model.Product;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service("FakeStoreProductService")
public class FakeStoreProductService implements ProductService{

    private final RestTemplate restTemplate;

    public FakeStoreProductService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public Product getSingleProduct(Long productId) throws ProductNotFoundException {

        // Fetch the FakeStoreProductDto from the API
        FakeStoreProductDto fsDto = restTemplate.getForObject(
                "https://fakestoreapi.com/products/" + productId,
                FakeStoreProductDto.class
        );

        // Convert the response to a Product if it's not null
        if (fsDto != null) {
            return fsDto.toProduct();
        } else {
            throw new ProductNotFoundException("Product with ID " + productId + " not found");
        }
    }

    @Override
    public Product createProduct(Product product) throws ProductNotFoundException {

        // Convert the Product to a FakeStoreProductDto
        FakeStoreProductDto fsDto = FakeStoreProductDto.fromProduct(product);

        // Send a POST request to the fake store API and get the response
        FakeStoreProductDto response = restTemplate.postForObject(
                "https://fakestoreapi.com/products",
                fsDto,
                FakeStoreProductDto.class
        );

        // Convert the response to a Product if it's not null
        if (response != null) {
            return response.toProduct();
        } else {
            throw new ProductNotFoundException("Failed to create product");
        }
    }

    @Override
    public List<Product> getAllProducts() throws ProductNotFoundException {
        // Fetch the array of FakeStoreProductDto from the API
        FakeStoreProductDto[] res = restTemplate.getForObject(
                "https://fakestoreapi.com/products",
                FakeStoreProductDto[].class
        );

        // Check if the response is null or empty
        if (res == null || res.length == 0) {
            throw new ProductNotFoundException("No products found");
        }

        List<Product> products = new ArrayList<>();

        // Convert each FakeStoreProductDto to a Product and add to the list
        for (FakeStoreProductDto fs : res) {
            products.add(fs.toProduct());
        }

        return products;
    }

    @Override
    public Product updateProduct(Long productId, Product product) throws ProductNotFoundException {
        // Convert Product to a FakeStoreProductDto
        FakeStoreProductDto fsDto = FakeStoreProductDto.fromProduct(product);

        // Set the headers
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");

        // Create the HTTP entity
        HttpEntity<FakeStoreProductDto> entity = new HttpEntity<>(fsDto, headers);

        // Update the product on the API
        ResponseEntity<FakeStoreProductDto> response = restTemplate.exchange(
                "https://fakestoreapi.com/products/" + productId,
                HttpMethod.PUT,
                entity,
                FakeStoreProductDto.class
        );

        // Check if the update was successful
        if (response.getStatusCode().is2xxSuccessful()) {
            // Fetch and return the updated product
            return Objects.requireNonNull(response.getBody()).toProduct();
        } else {
            throw new ProductNotFoundException("Failed to update product with ID " + productId);
        }
    }

    @Override
    public HttpStatus deleteProduct(Long productId) throws ProductNotFoundException {
        // Send the DELETE request
        ResponseEntity<String> response = restTemplate.exchange(
                "https://fakestoreapi.com/products/" + productId,
                HttpMethod.DELETE,
                null,
                String.class
        );

        // Check if the delete was successful
        if (response.getStatusCode().is2xxSuccessful()) {
            return HttpStatus.OK;
        } else {
            throw new ProductNotFoundException("Failed to delete product with ID " + productId);
        }
    }
}