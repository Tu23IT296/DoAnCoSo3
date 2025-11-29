package com.example.taskmaster.controller;

import com.example.taskmaster.dto.UserDTO;
import com.example.taskmaster.model.User;
import com.example.taskmaster.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/me")
    public ResponseEntity<UserDTO> getCurrentUser() {
        User user = userService.getCurrentUser();
        if (user != null) {
            UserDTO userDTO = new UserDTO();
            userDTO.setUid(user.getUid());
            userDTO.setEmail(user.getEmail());
            userDTO.setName(user.getName());
            return ResponseEntity.ok(userDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/me")
    public ResponseEntity<Void> deleteCurrentUser() {
        User currentUser = userService.getCurrentUser();
        userService.deleteUser(currentUser.getUid());
        return ResponseEntity.noContent().build();
    }
}