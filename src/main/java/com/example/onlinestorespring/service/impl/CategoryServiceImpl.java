package com.example.onlinestorespring.service.impl;

import com.example.onlinestorespring.model.Category;
import com.example.onlinestorespring.model.Product;
import com.example.onlinestorespring.repository.CategoryRepository;
import com.example.onlinestorespring.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    @Override
    public Category findById(int id) {
        return categoryRepository.findById(id).orElse(null);
    }

    @Override
    public Category save(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public void deleteById(int id) {
        categoryRepository.deleteById(id);
    }

    @Override
    public Category getCategoryById(Integer categoryId) {
        return categoryRepository.findById(categoryId).orElse(null);
    }
}
