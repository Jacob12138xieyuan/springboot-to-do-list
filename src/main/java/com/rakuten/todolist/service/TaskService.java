package com.rakuten.todolist.service;

import com.rakuten.todolist.entity.Task;
import com.rakuten.todolist.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {
    @Autowired
    private TaskRepository taskRepository;

    public List<Task> findAll() {
        return taskRepository.findAll(Sort.by(Sort.Direction.DESC, "createDate"));
    }
    public Task findById(int taskId) {
        Optional<Task> result = taskRepository.findById(taskId);
        Task task = null;
        if (result.isPresent()) {
            task = result.get();
        } else {
            throw new RuntimeException("Task not found");
        }
        return task;
    }

    public Task save(Task task) {
        return taskRepository.save(task);
    }

    public void deleteById(int taskId) {
        taskRepository.deleteById(taskId);
    }
}
