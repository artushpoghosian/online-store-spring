package com.example.onlinestorespring.service;

import com.example.onlinestorespring.model.Category;
import com.example.onlinestorespring.model.Product;

import java.util.List;

public interface ProductService {

    List<Product> findAll();

    Product findById(int id);

    Product save(Product product);

    void deleteById(int id);

    List<Product> findByCategoryId(Integer categoryId);

    Product updateProduct(int id, String title, double price, String description);

    List<Product> getProductsByCategory(Category category);
}
