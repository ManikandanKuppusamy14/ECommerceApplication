package com.sample.ecommerceapplication.service;

import com.sample.ecommerceapplication.dto.FakeStoreProductDto;
import com.sample.ecommerceapplication.exception.ProductNotFoundException;
import com.sample.ecommerceapplication.model.Product;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service("FakeStoreProductService")
public class FakeStoreProductService implements ProductService{

    private final RestTemplate restTemplate;

    public FakeStoreProductService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public Product getSingleProduct(Long productId) throws ProductNotFoundException {
        // Fetch the FakeStoreProductDto from the API
        ResponseEntity<FakeStoreProductDto> responseEntity = restTemplate.getForEntity(
                "https://fakestoreapi.com/products/" + productId,
                FakeStoreProductDto.class
        );

        // Check the response status
        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            FakeStoreProductDto fakeStoreProductDto = responseEntity.getBody();

            // Convert the response to a Product if it's not null
            if (fakeStoreProductDto != null) {
                return fakeStoreProductDto.toProduct();
            } else {
                throw new ProductNotFoundException("Product with ID " + productId + " not found");
            }
        } else {
            throw new ProductNotFoundException("Product with ID " + productId + " not found");
        }
    }

    @Override
    public Product createProduct(Product product) throws ProductNotFoundException {
       // Convert the Product to a FakeStoreProductDto
        FakeStoreProductDto fsDto = FakeStoreProductDto.fromProduct(product);

        // Send a POST request to the fake store API and get the response
        ResponseEntity<FakeStoreProductDto> responseEntity = restTemplate.postForEntity(
                "https://fakestoreapi.com/products",
                fsDto,
                FakeStoreProductDto.class
        );

        if (responseEntity.getStatusCode() == HttpStatus.CREATED) {
            FakeStoreProductDto fakeStoreProductDto = responseEntity.getBody();

            // Convert the response to a Product if it's not null
            if (fakeStoreProductDto != null) {
                return fakeStoreProductDto.toProduct();
            } else {
                throw new ProductNotFoundException("Failed to create product");
            }
        } else {
            throw new ProductNotFoundException("Failed to create product");
        }
    }

    @Override
    public List<Product> getAllProducts() throws ProductNotFoundException {
        List<Product> products = new ArrayList<>();
        // Fetch the array of FakeStoreProductDto from the API
        FakeStoreProductDto[] res = restTemplate.getForObject(
                "https://fakestoreapi.com/products",
                FakeStoreProductDto[].class
        );

        // Check if the response is null or empty
        if (res == null || res.length == 0) {
            throw new ProductNotFoundException("No products found");
        }

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

        // Update the product on the API
        restTemplate.put(
                "https://fakestoreapi.com/products/" + productId,
                fsDto
        );

        // Fetch and return the updated product
        return getSingleProduct(productId);
    }

    @Override
    public void deleteProduct(Long productId) throws ProductNotFoundException{
        restTemplate.delete("https://fakestoreapi.com/products/" + productId);
    }
}