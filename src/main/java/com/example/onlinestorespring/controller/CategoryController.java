package com.example.onlinestorespring.controller;

import com.example.onlinestorespring.model.Category;
import com.example.onlinestorespring.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping("/categories")
    public String categories(ModelMap modelMap, Authentication auth) {
        List<Category> categories = categoryService.findAll();
        modelMap.addAttribute("categories", categories);
        if (auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
            return "admin/categories";
        } else {
            return "user/categories";
        }
    }

    @GetMapping("/{id}")
    public String viewCategory(@PathVariable int id, Model model) {
        Category category = categoryService.getCategoryById(id);
        model.addAttribute("category", category);
        model.addAttribute("products", category.getProducts());
        return "categories";
    }

    @GetMapping("/categories/add")
    public String addCategory(ModelMap modelMap) {
        modelMap.addAttribute("category", new Category());
        return "admin/addCategory";
    }

    @PostMapping("/categories/add")
    public String addCategory(@ModelAttribute Category category) {
        categoryService.save(category);
        return "redirect:/categories";
    }

    @GetMapping("/categories/update")
    public String updateCategory(@RequestParam("id") int id, ModelMap modelMap) {
        Category category = categoryService.findById(id);
        modelMap.addAttribute("category", category);
        return "admin/updateCategory";
    }

    @PostMapping("/categories/update")
    public String updateCategory(@ModelAttribute Category category) {
        categoryService.save(category);
        return "redirect:/categories";
    }

    @GetMapping("/categories/delete")
    public String deleteCategory(@RequestParam("id") int id) {
        categoryService.deleteById(id);
        return "redirect:/categories";
    }

}
