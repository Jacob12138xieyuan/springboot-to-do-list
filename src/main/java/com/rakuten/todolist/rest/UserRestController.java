package com.rakuten.todolist.rest;

import com.rakuten.todolist.common_class.ApiResponse;
import com.rakuten.todolist.dto.UserRequest;
import com.rakuten.todolist.dto.UserResponse;
import com.rakuten.todolist.entity.User;
import com.rakuten.todolist.exception.ValidationException;
import com.rakuten.todolist.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class UserRestController {
    @Autowired
    private UserService userService;

    // Get all users
    @GetMapping("/users")
    public ResponseEntity<Object> findAllUsers() {
        List<User> users = userService.findAll();
        List<UserResponse> userResponses = users.stream().map(user -> new UserResponse(user)).collect(Collectors.toList());
        return ApiResponse.generateResponse("Users", HttpStatus.OK, userResponses);
    }

    // Get user by id
    @GetMapping("/users/{userId}")
    public ResponseEntity<Object> findUserById(@PathVariable int userId){
        User user = userService.findById(userId);
        if (user != null) {
            UserResponse userResponse = new UserResponse(user);
            return ApiResponse.generateResponse("User found", HttpStatus.OK, userResponse);
        } else {
            return ApiResponse.generateResponse("User not found", HttpStatus.NOT_FOUND, null);
        }
    }

    // Validate and create new user
    @PostMapping("/users")
    public ResponseEntity<Object> addUser(@Validated(UserRequest.OnCreate.class) @RequestBody UserRequest userRequest) throws ValidationException {
        User existingUser = userService.findByEmail(userRequest.getEmail());
        if (existingUser != null) {
            return ApiResponse.generateResponse("Email already registered!", HttpStatus.BAD_REQUEST, null);
        }
        UserResponse addedUserResponse = new UserResponse(userService.addUser(userRequest));
        return ApiResponse.generateResponse("User created", HttpStatus.CREATED, addedUserResponse);
    }

    // Authenticate user to sign in
    @PostMapping("/users/login")
    public ResponseEntity<Object> authenticateUser(@Validated(UserRequest.OnCreate.class) @RequestBody UserRequest userRequest) {
        User existingUser = userService.findByEmail(userRequest.getEmail());
        if (existingUser == null){
            return ApiResponse.generateResponse("Email is not registered!", HttpStatus.BAD_REQUEST, null);
        }
        User authenticatedUser = userService.authenticate(userRequest.getEmail(), userRequest.getPassword());
        if (authenticatedUser == null){
            return ApiResponse.generateResponse("Email or password is not correct!", HttpStatus.UNAUTHORIZED, null);
        }
        UserResponse authenticatedUserResponse = new UserResponse(authenticatedUser);
        return ApiResponse.generateResponse("Successfully authenticated!", HttpStatus.OK, authenticatedUserResponse);
    }

    // User update password
    @PutMapping("/users/{userId}/password")
    public ResponseEntity<Object> updateUserPassword(@Validated(UserRequest.OnUpdatePassword.class) @RequestBody UserRequest userRequest, @PathVariable int userId) throws ValidationException{
        User existingUser = userService.findById(userId);
        if (existingUser == null) {
            return ApiResponse.generateResponse("User not found", HttpStatus.NOT_FOUND, null);
        }
        existingUser.setPassword(userRequest.getPassword());
        User updatedUser = userService.update(existingUser);
        UserResponse updatedUserResponse = new UserResponse(updatedUser);
        return ApiResponse.generateResponse("User password updated", HttpStatus.OK, updatedUserResponse);
    }

    // User update profile
    @PutMapping("/users/{userId}/profile")
    public ResponseEntity<Object> updateUserProfile(@Validated(UserRequest.OnUpdate.class) @RequestBody UserRequest userRequest, @PathVariable int userId) throws ValidationException{
        User existingUser = userService.findById(userId);
        if (existingUser == null) {
            return ApiResponse.generateResponse("User not found", HttpStatus.NOT_FOUND, null);
        }
        existingUser.setName(userRequest.getName());
        User updatedUser = userService.update(existingUser);
        UserResponse updatedUserResponse = new UserResponse(updatedUser);
        return ApiResponse.generateResponse("User profile updated", HttpStatus.OK, updatedUserResponse);
    }

    // Delete user
    @DeleteMapping("/users/{userId}")
    public ResponseEntity<Object> deleteUser(@PathVariable int userId) {
        User user = userService.findById(userId);
        if (user == null) {
            return ApiResponse.generateResponse("User not found", HttpStatus.NOT_FOUND, null);
        }
        userService.deleteById(userId);
        return ApiResponse.generateResponse("User with id " + userId + " is deleted", HttpStatus.OK, null);
    }
}
