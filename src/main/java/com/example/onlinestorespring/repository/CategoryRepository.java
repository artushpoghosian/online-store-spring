package com.example.onlinestorespring.repository;

import com.example.onlinestorespring.model.Category;
import com.example.onlinestorespring.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
}
