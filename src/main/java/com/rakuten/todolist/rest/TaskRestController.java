package com.rakuten.todolist.rest;

import com.rakuten.todolist.entity.Task;
import com.rakuten.todolist.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class TaskRestController {
    @Autowired
    private TaskService taskService;

    @GetMapping("/tasks")
    public List<Task> findAllTasks() {
        return taskService.findAll();
    }

    @GetMapping("/tasks/{taskId}")
    public Task findTaskById(@PathVariable int taskId){
        Task task = taskService.findById(taskId);
        if (task == null) {
            throw  new RuntimeException("Task not found");
        }
        return task;
    }

    @PostMapping("/tasks")
    public Task addTask(@RequestBody Task task) {
        task.setId(0);
        taskService.save(task);
        return task;
    }

    @PutMapping("/tasks")
    public Task updateTask(@RequestBody Task task) {
        Task updatedTask = taskService.findById(task.getId());
        updatedTask.setName(task.getName());
        updatedTask.setFinished(task.getFinished());
        return taskService.save(updatedTask);
    }

    @DeleteMapping("/tasks/{taskId}")
    public String deleteTask(@PathVariable int taskId) {
        Task task = taskService.findById(taskId);
        if (task == null) {
            throw new RuntimeException("Task not found");
        }
        taskService.deleteById(taskId);
        return "Deleted task id " + task.getId();
    }

}
