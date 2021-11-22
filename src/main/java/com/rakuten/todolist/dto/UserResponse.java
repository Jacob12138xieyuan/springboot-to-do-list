package com.rakuten.todolist.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.rakuten.todolist.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
    private int id;

    private String name;

    private String email;

    @JsonIgnore
    private String password;

    public UserResponse(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
        this.password = user.getPassword();
    }
}
