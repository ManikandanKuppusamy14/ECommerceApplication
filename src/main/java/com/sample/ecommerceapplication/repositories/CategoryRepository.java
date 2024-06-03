package com.sample.ecommerceapplication.repositories;

import com.sample.ecommerceapplication.model.Category;
import com.sample.ecommerceapplication.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Category save(Category category);
    Category findByTitle(String title);

    @Query(value = "select * from Category" , nativeQuery = true)
    List<Category> getAllCategories();
}
