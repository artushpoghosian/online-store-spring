package com.example.onlinestorespring.controller;

import com.example.onlinestorespring.model.Category;
import com.example.onlinestorespring.model.User;
import com.example.onlinestorespring.service.CategoryService;
import com.example.onlinestorespring.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final CategoryService categoryService;

    @GetMapping("/admin/users")
    public String categories(ModelMap modelMap) {
        List<User> users = userService.findAll();
        modelMap.addAttribute("users", users);
        return "admin/users";
    }

}
