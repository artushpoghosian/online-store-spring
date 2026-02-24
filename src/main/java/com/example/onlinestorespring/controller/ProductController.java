package com.example.onlinestorespring.controller;

import com.example.onlinestorespring.dto.ProductSearchCriteria;
import com.example.onlinestorespring.model.Category;
import com.example.onlinestorespring.model.Product;
import com.example.onlinestorespring.model.User;
import com.example.onlinestorespring.service.CategoryService;
import com.example.onlinestorespring.service.ProductService;
import com.example.onlinestorespring.service.UserService;
import com.example.onlinestorespring.specification.ProductSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@Controller
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final CategoryService categoryService;
    private final UserService userService;

    @GetMapping("/products")
    public String products(@RequestParam(required = false) Integer categoryId,
                           ModelMap modelMap,
                           @RequestParam("page") Optional<Integer> page,
                           @RequestParam("size") Optional<Integer> size,
                           @RequestParam Optional<String> sortField,
                           @RequestParam Optional<String> sortDir,
                           @ModelAttribute ProductSearchCriteria searchCriteria,
                           Authentication auth) {

        int currentPage = page.orElse(1);
        int pageSize = size.orElse(5);

        String field = sortField.orElse("createdAt");
        String direction = sortDir.orElse("desc");

        Sort sort = direction.equalsIgnoreCase("asc")
                ? Sort.by(field).ascending()
                : Sort.by(field).descending();

        PageRequest pageRequest = PageRequest.of(currentPage - 1, pageSize, sort);

        Page<Product> products;

        boolean noFilter =
                (searchCriteria.getTitle() == null || searchCriteria.getTitle().isBlank())
                        && searchCriteria.getMinPrice() == null
                        && searchCriteria.getMaxPrice() == null;

        if (noFilter) {
            products = productService.findAll(pageRequest);
        } else {
            ProductSpecification productSpecification = new ProductSpecification(searchCriteria);
            products = productService.findAllWithSpecification(productSpecification, pageRequest);
            modelMap.addAttribute("searchCriteria", searchCriteria);
        }
        modelMap.addAttribute("products", products);
        modelMap.addAttribute("sortField", field);
        modelMap.addAttribute("sortDir", direction);

        int totalPages = products.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .toList();
            modelMap.addAttribute("pageNumbers", pageNumbers);
        }
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
