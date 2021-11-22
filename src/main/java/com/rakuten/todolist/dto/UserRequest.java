package com.rakuten.todolist.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest {
    @Null(groups = { OnCreate.class })
    @NotNull(groups = { OnUpdate.class })
    @Pattern(regexp = "[a-zA-Z0-9]+", message = "Value must be alphanumeric.", groups = { OnUpdate.class })
    private String name;

    @NotNull(groups = { OnCreate.class })
    @Email(message = "Not valid email address.", groups = { OnCreate.class })
    private String email;

    @NotNull(groups = { OnCreate.class, OnUpdatePassword.class })
    @Pattern(regexp = "[a-zA-Z0-9]+", message = "Value must be alphanumeric.", groups = { OnCreate.class, OnUpdatePassword.class })
    private String password;

    public interface OnUpdate {
    }

    public interface OnUpdatePassword {
    }

    public interface OnCreate {
    }
}
