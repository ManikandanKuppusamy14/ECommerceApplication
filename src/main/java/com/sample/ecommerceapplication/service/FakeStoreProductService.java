package com.sample.ecommerceapplication.service;

import com.sample.ecommerceapplication.dto.FakeStoreProductDto;
import com.sample.ecommerceapplication.model.Product;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.List;

@Service
public class FakeStoreProductService implements ProductService{

    private final RestTemplate restTemplate;

    public FakeStoreProductService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public Product createProduct(Product product) {
        // Convert the Product to a FakeStoreProductDto
        FakeStoreProductDto fsDto = FakeStoreProductDto.fromProduct(product);

        // Send a POST request to the fake store API and get the response
        FakeStoreProductDto fakeStoreProductDto = restTemplate.postForObject(
                "https://fakestoreapi.com/products",
                fsDto,
                FakeStoreProductDto.class
        );

        // Convert the response to a Product if it's not null
        return fakeStoreProductDto != null ? fakeStoreProductDto.toProduct() : null;
    }

    @Override
    public Product getSingleProduct(Long productId) {
        // Fetch the FakeStoreProductDto from the API
        FakeStoreProductDto fakeStoreProductDto = restTemplate.getForObject(
                "https://fakestoreapi.com/products/" + productId,
                FakeStoreProductDto.class
        );

        // Convert the response to a Product if it's not null
        return fakeStoreProductDto != null ? fakeStoreProductDto.toProduct() : null;
    }

    @Override
    public List<Product> getAllProducts() {
        return List.of();
    }

    @Override
    public Product updateProduct(Long productId, Product product) {
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
    public void deleteProduct(Long productId) {
        restTemplate.delete("https://fakestoreapi.com/products/" + productId);
    }
}