package com.rakuten.todolist.service;

import com.rakuten.todolist.entity.User;
import com.rakuten.todolist.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public List<User> findAll() {
        return userRepository.findAll(Sort.by(Sort.Direction.DESC, "createDate"));
    }
    public User findById(int userId) {
        Optional<User> result = userRepository.findById(userId);
        User user = null;
        if (result.isPresent()) {
            user = result.get();
        } else {
            throw new RuntimeException("User not found");
        }
        return user;
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    public void deleteById(int userId) {
        userRepository.deleteById(userId);
    }

    public User authenticate(String email, String password) { return userRepository.authenticate(email, password);}

    public User findUserByEmail(String email) { return userRepository.findUserByEmail(email); }
}
