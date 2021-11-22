package com.rakuten.todolist.rest;

import com.rakuten.todolist.common_class.ApiResponse;
import com.rakuten.todolist.dto.TaskRequest;
import com.rakuten.todolist.dto.TaskResponse;
import com.rakuten.todolist.entity.Task;
import com.rakuten.todolist.service.TaskService;
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
public class TaskRestController {
    @Autowired
    private TaskService taskService;

    @Autowired
    private UserService userService;

    @GetMapping("/tasks")
    public ResponseEntity<Object> findAllTasks() {
        List<Task> tasks = taskService.findAll();
        List<TaskResponse> taskResponses = tasks.stream().map(task -> new TaskResponse(task)).collect(Collectors.toList());
        return ApiResponse.generateResponse("All tasks", HttpStatus.OK, taskResponses);
    }

    @GetMapping("/tasks/{taskId}")
    public ResponseEntity<Object> findTaskById(@PathVariable int taskId){
        Task task = taskService.findById(taskId);
        if (task == null) {
            return ApiResponse.generateResponse("Task not found", HttpStatus.NOT_FOUND, null);
        } else {
            TaskResponse taskResponse = new TaskResponse(task);
            return ApiResponse.generateResponse("Task found", HttpStatus.OK, taskResponse);
        }
    }

    @GetMapping("/tasks/user/{userId}")
    public ResponseEntity<Object> findTasksByUserId(@PathVariable int userId){
        List<Task> tasks = taskService.findTasksByUserId(userId);
        if (tasks == null) {
            return ApiResponse.generateResponse("User not found", HttpStatus.NOT_FOUND, null);
        }
        List<TaskResponse> taskResponses = tasks.stream().map(task -> new TaskResponse(task)).collect(Collectors.toList());
        return ApiResponse.generateResponse("User's tasks", HttpStatus.OK, taskResponses);
    }

    @PostMapping("/tasks")
    public ResponseEntity<Object> addTask(@Validated(TaskRequest.OnCreate.class) @RequestBody TaskRequest taskRequest) {
        TaskResponse addedTaskResponse = taskService.addTask(taskRequest);
        if (addedTaskResponse == null) {
            return ApiResponse.generateResponse("User not found", HttpStatus.NOT_FOUND, null);
        }
        return ApiResponse.generateResponse("Task created", HttpStatus.CREATED, addedTaskResponse);
    }

    @PutMapping("/tasks/{taskId}")
    public ResponseEntity<Object> updateTask(@Validated(TaskRequest.OnUpdate.class) @RequestBody TaskRequest taskRequest, @PathVariable int taskId) {
        Task existingTask = taskService.findById(taskId);
        if (existingTask == null) {
            return ApiResponse.generateResponse("Task not found", HttpStatus.NOT_FOUND, null);
        }
        existingTask.setName(taskRequest.getName());
        existingTask.setFinished(taskRequest.isFinished());
        TaskResponse taskResponse = new TaskResponse(taskService.update(existingTask));
        return ApiResponse.generateResponse("Task updated", HttpStatus.OK, taskResponse);
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
