package com.rakuten.todolist.repository;

import com.rakuten.todolist.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


public interface TaskRepository extends JpaRepository<Task, Integer> {
    List<Task> findByUserIdOrderByCreateDateDesc(int userId);

    @Modifying
    @Transactional
    @Query("Delete From Task where finished = 1")
    Integer deleteAllFinishedTasks ();
}
