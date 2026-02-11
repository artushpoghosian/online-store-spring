package com.example.onlinestorespring.repository;

import com.example.onlinestorespring.model.Category;
import com.example.onlinestorespring.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer> {

    List<Product> findByCategoryId(Integer categoryId);
}
