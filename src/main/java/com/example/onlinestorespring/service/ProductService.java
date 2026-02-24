package com.example.onlinestorespring.service;

import com.example.onlinestorespring.model.Category;
import com.example.onlinestorespring.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public interface ProductService {

    Page<Product> findAll(Pageable pageable);

    Product findById(int id);

    Product save(Product product);

    void deleteById(int id);

    List<Product> findByCategoryId(Integer categoryId);

    Product updateProduct(int id, String title, double price, String description);

    List<Product> getProductsByCategory(Category category);

    Page<Product> findAllWithSpecification(Specification<Product> specification, Pageable pageable);
}
