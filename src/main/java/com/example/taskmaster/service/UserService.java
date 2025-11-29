package com.example.taskmaster.service;

import com.example.taskmaster.model.User;
import com.example.taskmaster.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(String uid) {
        return userRepository.findById(uid);
    }

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public void deleteUser(String uid) {
        userRepository.deleteById(uid);
    }

    public User getUserByUid(String uid) {
        return userRepository.findById(uid).orElse(null);
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User getCurrentUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return getUserByEmail(email);
    }
}