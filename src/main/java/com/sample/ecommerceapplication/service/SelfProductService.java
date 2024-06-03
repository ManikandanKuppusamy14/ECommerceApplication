package com.sample.ecommerceapplication.service;

import com.sample.ecommerceapplication.exception.ProductNotFoundException;
import com.sample.ecommerceapplication.model.Category;
import com.sample.ecommerceapplication.model.Product;
import com.sample.ecommerceapplication.repositories.CategoryRepository;
import com.sample.ecommerceapplication.repositories.ProductRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service("SelfProductService")
public class SelfProductService implements ProductService{

    private final ProductRepository productRepository;
    private final CategoryRepository  categoryRepository;

    public SelfProductService(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Product createProduct(Product product) {

        Category category = categoryRepository.findByTitle(product.getCategory().getTitle());

        if(category == null) {
            Category newCategory = new Category();
            newCategory.setTitle(product.getCategory().getTitle());
            categoryRepository.save(newCategory);
            product.setCategory(newCategory);
        } else {
            product.setCategory(category);
        }

        return productRepository.save(product);
    }

    @Override
    public Product getSingleProduct(Long productId) throws ProductNotFoundException {
        Optional<Product> p = productRepository.findById(productId);
        if(p.isPresent()) {
            return p.get();
        } else {
            throw new ProductNotFoundException("Product not found");
        }
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public HttpStatus deleteProduct(Long productId) {
        Optional<Product> p = productRepository.findById(productId);
        if(p.isPresent()) {
            productRepository.deleteById(productId);
        }
        return HttpStatus.OK;
    }

    @Override
    public Product updateProduct(Long productId, Product product) throws ProductNotFoundException {
        Optional<Product> optionalProduct = productRepository.findById(productId);
        if (optionalProduct.isPresent()) {
            Product existingProduct = optionalProduct.get();

            existingProduct.setTitle(product.getTitle());
            existingProduct.setPrice(product.getPrice());
            existingProduct.setCategory(product.getCategory());
            existingProduct.setDescription(product.getDescription());
            existingProduct.setImageUrl(product.getImageUrl());

            return productRepository.save(existingProduct);
        } else {
            throw new ProductNotFoundException("Product with ID " + productId + " not found");
        }
    }
}
