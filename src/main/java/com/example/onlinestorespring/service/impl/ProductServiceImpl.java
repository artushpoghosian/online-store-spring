package com.example.onlinestorespring.service.impl;

import com.example.onlinestorespring.model.Category;
import com.example.onlinestorespring.model.Product;
import com.example.onlinestorespring.repository.ProductRepository;
import com.example.onlinestorespring.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public Page<Product> findAll(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    @Override
    public Product findById(int id) {
        return productRepository.findById(id).orElse(null);
    }

    @Override
    public Product save(Product product) {
        return productRepository.save(product);
    }

    @Override
    public void deleteById(int id) {
        productRepository.deleteById(id);
    }

    @Override
    public List<Product> findByCategoryId(Integer categoryId) {
        return productRepository.findByCategoryId(categoryId);
    }

    @Override
    public Product updateProduct(int id, String title, double price, String description) {
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        existingProduct.setTitle(title);
        existingProduct.setPrice(price);
        existingProduct.setDescription(description);

        return productRepository.save(existingProduct);
    }

    @Override
    public List<Product> getProductsByCategory(Category category) {
        return List.of();
    }

    @Override
    public Page<Product> findAllWithSpecification(Specification<Product> specification, Pageable pageable) {
        return productRepository.findAll(specification, pageable);
    }


}
