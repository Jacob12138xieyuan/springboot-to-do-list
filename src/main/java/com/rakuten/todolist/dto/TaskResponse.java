package com.rakuten.todolist.dto;

import com.rakuten.todolist.entity.Task;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskResponse {
    private int id;

    private String name;

    private boolean finished;

    private int userId;

    private LocalDateTime createDate;

    public TaskResponse(Task task) {
        this.id = task.getId();
        this.name = task.getName();
        this.finished = task.isFinished();
        this.userId = task.getUser().getId();
        this.createDate = task.getCreateDate();
    }
}
