package com.rakuten.todolist.rest;

import com.rakuten.todolist.common_class.ApiResponse;
import com.rakuten.todolist.common_class.LoginForm;
import com.rakuten.todolist.entity.User;
import com.rakuten.todolist.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class UserRestController {
    @Autowired
    private UserService userService;

    // Get all users
    @GetMapping("/users")
    public List<User> findAllUsers() {
        return userService.findAll();
    }

    // Get user by id
    @GetMapping("/users/{userId}")
    public User findUserById(@PathVariable int userId){
        User user = userService.findById(userId);
        if (user == null) {
            throw new RuntimeException("User not found");
        }
        return user;
    }

    // Validate and create new user
    @PostMapping("/users")
    public ResponseEntity<Object> addUser(@RequestBody User user) {
        User existingUser = userService.findUserByEmail(user.getEmail());
        if (existingUser != null) {
            return ApiResponse.generateResponse("Email already registered!", HttpStatus.BAD_REQUEST, null);
        }
        user.setId(0);
        userService.save(user);
        return ApiResponse.generateResponse("User created", HttpStatus.CREATED, user);
    }

    // Authenticate user to sign in
    @PostMapping("/users/login")
    public ResponseEntity<Object> authenticateUser(@Valid @RequestBody LoginForm loginForm) {
        String email = loginForm.getEmail();
        String password = loginForm.getPassword();
        User user = userService.findUserByEmail(email);
        if (user == null){
            return ApiResponse.generateResponse("Email is not registered!", HttpStatus.BAD_REQUEST, null);
        }
        User authenticatedUser = userService.authenticate(email, password);
        if (authenticatedUser != null){
            return ApiResponse.generateResponse("Successfully authenticated!", HttpStatus.OK, authenticatedUser.getId());
        } else {
            return ApiResponse.generateResponse("Email or password is not correct!", HttpStatus.UNAUTHORIZED, null);
        }
    }

    // User change password
    @PutMapping("/users")
    public User updateUserPassword(@RequestBody Map<String, Object> data) {
        User updatedUser = userService.findById((int) data.get("id"));
        updatedUser.setPassword((String) data.get("newPassword"));
        return userService.save(updatedUser);
    }

    @DeleteMapping("/users/{userId}")
    public String deleteUser(@PathVariable int userId) {
        User user = userService.findById(userId);
        if (user == null) {
            throw new RuntimeException("User not found");
        }
        userService.deleteById(userId);
        return "Deleted user id " + user.getId();
    }
}
