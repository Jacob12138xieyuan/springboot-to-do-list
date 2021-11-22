package com.rakuten.todolist.dto;

import com.rakuten.todolist.entity.Task;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskRequest {
    @NotNull(groups = { TaskRequest.OnCreate.class, TaskRequest.OnUpdate.class })
    private String name;

    @NotNull(groups = { TaskRequest.OnCreate.class, TaskRequest.OnUpdate.class })
    private boolean finished;

    // default value for int is 0
    @Max(groups = {TaskRequest.OnUpdate.class}, value = 0)
    @Min(groups = { TaskRequest.OnCreate.class }, value = 1)
    private int userId;

    public TaskRequest(Task task) {
        this.name = task.getName();
        this.finished = task.isFinished();
        this.userId = task.getUser().getId();
    }

    public interface OnUpdate {
    }

    public interface OnCreate {
    }
}
