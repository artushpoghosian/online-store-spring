package com.example.onlinestorespring.service;

import com.example.onlinestorespring.model.Category;
import com.example.onlinestorespring.model.Product;

import java.util.List;

public interface CategoryService {

    List<Category> findAll();

    Category findById(int id);

    Category save(Category category);

    void deleteById(int id);

    Category getCategoryById(Integer categoryId);
}
