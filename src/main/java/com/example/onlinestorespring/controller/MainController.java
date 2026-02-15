package com.example.onlinestorespring.controller;

import com.example.onlinestorespring.model.User;
import com.example.onlinestorespring.security.SpringUser;
import com.example.onlinestorespring.service.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Controller
@RequiredArgsConstructor
public class MainController {

    @Value("${online.store.uploaded.images.directory.path}")
    private String imageDirectoryPath;

    private final UserService userService;

    @GetMapping("/")
    public String mainPage(@AuthenticationPrincipal SpringUser userPrincipal,
                           ModelMap modelMap) {
        if (userPrincipal != null) {
            modelMap.addAttribute("user", userPrincipal.getUser());
        }
        return "index";
    }

    @GetMapping("/login")
    public String login(@RequestParam(required = false) String msg, ModelMap modelMap) {
        modelMap.addAttribute("msg", msg);
        return "login";
    }

    @GetMapping("/register")
    public String register(@RequestParam(required = false) String msg, ModelMap modelMap) {
        modelMap.addAttribute("msg", msg);
        return "register";
    }

    @PostMapping("/register")
    public String register(@RequestParam("name") String name,
                           @RequestParam("surname") String surname,
                           @RequestParam("username") String username,
                           @RequestParam("password") String password,
                           @RequestParam("pic") MultipartFile multipartFile) {
        if (userService.findByUsername(username).isPresent()) {
            return "redirect:/register?msg=Username already exists! Please choose another username!";
        }
        User user = new User();
        user.setName(name);
        user.setSurname(surname);
        user.setUsername(username);
        user.setPassword(password);
        userService.save(user, multipartFile);

        return "redirect:/login?msg=Registered Successfully!";
    }

    @GetMapping("/image/get")
    public @ResponseBody byte[] getImage(@RequestParam("picName") String picName) {
        File file = new File(imageDirectoryPath + picName);
        if (file.exists() && file.isFile()) {
            try {
                return FileUtils.readFileToByteArray(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

}