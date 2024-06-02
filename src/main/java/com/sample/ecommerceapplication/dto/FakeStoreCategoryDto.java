package com.sample.ecommerceapplication.dto;

import com.sample.ecommerceapplication.model.Category;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FakeStoreCategoryDto {
    private Long id;
    private String title;

    public Category toCategory() {
        Category category = new Category();
        category.setId(this.id);
        category.setTitle(this.title);
        return category;
    }
}
