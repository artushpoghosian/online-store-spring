package com.example.onlinestorespring.service;

import com.example.onlinestorespring.model.User;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface UserService {

    List<User> findAll();

    User findById(int id);

    User save(User user, MultipartFile file);

    void delete(int id);

    Optional<User> findByUsername(String username);

}
