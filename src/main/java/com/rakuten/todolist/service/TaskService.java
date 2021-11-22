package com.rakuten.todolist.service;

import com.rakuten.todolist.dto.TaskRequest;
import com.rakuten.todolist.dto.TaskResponse;
import com.rakuten.todolist.entity.Task;
import com.rakuten.todolist.entity.User;
import com.rakuten.todolist.repository.TaskRepository;
import com.rakuten.todolist.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {
    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserRepository userRepository;

    public List<Task> findAll() {
        List<Task> tasks = taskRepository.findAll(Sort.by(Sort.Direction.DESC, "createDate"));
        return tasks;
    }

    public Task findById(int taskId) {
        Optional<Task> result = taskRepository.findById(taskId);
        Task task = null;
        if (result.isPresent()) {
            task = result.get();
        }
        return task;
    }

    public List<Task> findTasksByUserId(int userId) {
        List<Task> tasks = taskRepository.findByUserIdOrderByCreateDateDesc(userId);
        return tasks;
    }

    public TaskResponse addTask(TaskRequest taskRequest) {
        Task task = new Task(taskRequest);
        task.setId(0);

        Optional<User> result = userRepository.findById(taskRequest.getUserId());
        if (result.isPresent()) {
            User user = result.get();
            task.setUser(user);
            Task addedTask = taskRepository.save(task);
            TaskResponse addedTaskResponse = new TaskResponse(addedTask);
            return addedTaskResponse;
        }
        return null;
    }

    public Task update(Task task) {
        return taskRepository.save(task);
    }

    public void deleteById(int taskId) {
        taskRepository.deleteById(taskId);
    }

    // @Scheduled(cron = "0 0/1 * 1/1 * ?") // every minute
    @Scheduled(cron = "0 0 12 1/1 * ?") // daily
    public void deleteFinishedTasksDaily() {
        System.out.println(taskRepository.deleteAllFinishedTasks() + " finished tasks are deleted");
    }
}
