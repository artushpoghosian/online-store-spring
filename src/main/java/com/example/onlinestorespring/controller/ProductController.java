package com.example.onlinestorespring.controller;

import com.example.onlinestorespring.model.Category;
import com.example.onlinestorespring.model.Product;
import com.example.onlinestorespring.model.User;
import com.example.onlinestorespring.service.CategoryService;
import com.example.onlinestorespring.service.ProductService;
import com.example.onlinestorespring.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final CategoryService categoryService;
    private final UserService userService;

    @GetMapping("/products")
    public String products(@RequestParam(required = false) Integer categoryId,
                           ModelMap modelMap,
                           Authentication auth) {
        List<Product> products;
        if (categoryId != null) {
            products = productService.findByCategoryId(categoryId);
        } else {
            products = productService.findAll();
        }

        modelMap.addAttribute("products", products);
        if (auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
            return "admin/products";
        } else {
            return "user/products";
        }
    }

    @GetMapping("/products/{id}")
    public String productDetails(@PathVariable Integer id, ModelMap modelMap) {
        Product product = productService.findById(id);
        modelMap.addAttribute("product", product);
        return "user/product";
    }

    @GetMapping("/products/category")
    public String productsByCategory(@RequestParam("categoryId") Integer categoryId,
                                     ModelMap modelMap,
                                     Authentication auth) {
        Category category = categoryService.getCategoryById(categoryId);
        List<Product> products = productService.getProductsByCategory(category);
        modelMap.addAttribute("products", products);
        if (auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
            return "admin/products";
        } else {
            return "user/products";
        }
    }


    @GetMapping("/products/add")
    public String addProduct(ModelMap modelMap) {
        modelMap.addAttribute("product", new Product());
        modelMap.addAttribute("categories", categoryService.findAll());
        return "admin/addProduct";
    }

    @PostMapping("/products/add")
    public String addProduct(@ModelAttribute Product product, Authentication auth) {
        User user = userService.findByUsername(auth.getName()).orElseThrow(() -> new RuntimeException("User not found"));
        product.setUser(user);

        productService.save(product);
        return "redirect:/products";
    }


    @GetMapping("/products/update")
    public String updateProduct(@RequestParam("id") int id, ModelMap modelMap) {
        Product product = productService.findById(id);
        modelMap.addAttribute("product", product);
        modelMap.addAttribute("categories", categoryService.findAll());
        return "admin/updateProduct";
    }

    @PostMapping("/products/update")
    public String updateProduct(@ModelAttribute Product updatedProduct) {
        Product existingProduct = productService.findById(updatedProduct.getId());

        existingProduct.setTitle(updatedProduct.getTitle());
        existingProduct.setPrice(updatedProduct.getPrice());
        existingProduct.setDescription(updatedProduct.getDescription());

        productService.save(existingProduct);
        return "redirect:/products";
    }

    @GetMapping("/products/delete")
    public String deleteProduct(@RequestParam("id") int id) {
        productService.deleteById(id);
        return "redirect:/products";
    }
}
