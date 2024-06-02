package com.sample.ecommerceapplication.dto;

import com.sample.ecommerceapplication.model.Category;
import com.sample.ecommerceapplication.model.Product;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FakeStoreProductDto {
    private long id;
    private String title;
    private double price;
    private String category;
    private String description;
    private String image;

    public static FakeStoreProductDto fromProduct(Product product) {
        FakeStoreProductDto fsDto = new FakeStoreProductDto();
        fsDto.setId(product.getId());
        fsDto.setTitle(product.getTitle());
        fsDto.setPrice(product.getPrice());
        fsDto.setDescription(product.getDescription());
        fsDto.setImage(product.getImageUrl());
        fsDto.setCategory(product.getCategory().getTitle());

        return fsDto;
    }

    public Product toProduct() {
        Product product = new Product();
        product.setId(id);
        product.setTitle(title);
        product.setPrice(price);
        product.setDescription(description);
        product.setImageUrl(image);

        Category cat = new Category();
        cat.setTitle(category);

        product.setCategory(cat);

        return product;
    }
}
