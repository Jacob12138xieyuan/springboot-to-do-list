package com.rakuten.todolist.repository;

import com.rakuten.todolist.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Integer> {
    @Query(value = "SELECT * FROM user WHERE email = LOWER(:email) and password = LOWER(:password)", nativeQuery = true)
    User authenticate(String email, String password);

    @Query(value = "SELECT * FROM user WHERE email = LOWER(:email)", nativeQuery = true)
    User findUserByEmail(String email);
}
