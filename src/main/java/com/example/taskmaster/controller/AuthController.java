package com.example.taskmaster.controller;

import com.example.taskmaster.dto.UserDTO;
import com.example.taskmaster.model.User;
import com.example.taskmaster.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<UserDTO> register(@RequestBody User user) {
        // Kiểm tra xem email đã tồn tại chưa
        if (userService.getUserByEmail(user.getEmail()) != null) {
            return ResponseEntity.badRequest().body(null);
        }

        // Mã hóa mật khẩu
        user.setPasswordHash(passwordEncoder.encode(user.getPasswordHash()));
        User savedUser = userService.createUser(user);

        UserDTO userDTO = new UserDTO();
        userDTO.setUid(savedUser.getUid());
        userDTO.setEmail(savedUser.getEmail());
        userDTO.setName(savedUser.getName());

        return ResponseEntity.status(201).body(userDTO);
    }

    @PostMapping("/login")
    public ResponseEntity<UserDTO> login(@RequestBody User loginRequest) {
        User user = userService.getUserByEmail(loginRequest.getEmail());
        if (user != null && passwordEncoder.matches(loginRequest.getPasswordHash(), user.getPasswordHash())) {
            UserDTO userDTO = new UserDTO();
            userDTO.setUid(user.getUid());
            userDTO.setEmail(user.getEmail());
            userDTO.setName(user.getName());
            return ResponseEntity.ok(userDTO);
        }
        return ResponseEntity.status(401).body(null);
    }
}