package com.example.onlinestorespring.service.impl;

import com.example.onlinestorespring.model.User;
import com.example.onlinestorespring.model.UserType;
import com.example.onlinestorespring.repository.UserRepository;
import com.example.onlinestorespring.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${online.store.uploaded.images.directory.path}")
    private String imageDirectoryPath;

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User findById(int id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public User save(User user, MultipartFile multipartFile) {
        if (!multipartFile.isEmpty() && multipartFile != null) {
            String fileName = System.currentTimeMillis() + "_" + multipartFile.getOriginalFilename();
            File file = new File(imageDirectoryPath + fileName);
            try {
                multipartFile.transferTo(file);
                user.setPictureName(fileName);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        user.setUserType(UserType.USER);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public void delete(int id) {
        userRepository.deleteById(id);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
