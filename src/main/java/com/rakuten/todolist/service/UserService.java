package com.rakuten.todolist.service;

import com.rakuten.todolist.dto.UserRequest;
import com.rakuten.todolist.entity.User;
import com.rakuten.todolist.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public List<User> findAll() {
        List<User> users = userRepository.findAll();
        return users;
    }

    public User findById(int userId) {
        Optional<User> result = userRepository.findById(userId);
        User user = null;
        if (result.isPresent()) {
            user = result.get();
        }
        return user;
    }

    public User update(User user) {
        return userRepository.save(user);
    }

    public User addUser(UserRequest userRequest) {
        User user = new User(userRequest);
        user.setId(0);
        return userRepository.save(user);
    }

    public void deleteById(int userId) {
        userRepository.deleteById(userId);
    }

    public User authenticate(String email, String password) {
        List<User> users = userRepository.findByEmailAndPassword(email.toLowerCase(Locale.ROOT), password.toLowerCase(Locale.ROOT));
        User user = null;
        if (users.size() > 0){
            user = users.get(0);
        }
        return user;
    }

    public User findByEmail(String email) {
        User existingUser = userRepository.findByEmail(email.toLowerCase(Locale.ROOT));
        User user = null;
        if (existingUser != null){
            user = existingUser;
        }
        return user;
    }
}
