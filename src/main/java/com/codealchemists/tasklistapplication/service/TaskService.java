package com.codealchemists.tasklistapplication.service;

import com.codealchemists.tasklistapplication.model.Task;
import com.codealchemists.tasklistapplication.model.TaskStatus;
import com.codealchemists.tasklistapplication.model.User;
import com.codealchemists.tasklistapplication.repository.TaskRepository;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.UUID;

@Slf4j
public class TaskService {
    private final TaskRepository taskRepository;

    public TaskService() {
        this(new TaskRepository());
    }

    // Constructor Injection for Testing
    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public List<Task> getTasksForUser(User user) {
        return taskRepository.findAllByUser(user.getId());
    }

    public void createTask(String title, String shortDesc, String longDesc, User user) {
        log.info("Creating new task '{}' for user '{}'", title, user.getUsername());
        Task task = new Task();
        task.setTitle(title);
        task.setShortDescription(shortDesc);
        task.setLongDescription(longDesc);
        task.setStatus(TaskStatus.TO_DO); // Default status
        task.setUser(user);
        taskRepository.save(task);
    }

    public void updateTaskStatus(String taskId, String statusStr) {
        try {
            UUID id = UUID.fromString(taskId);
            Task task = taskRepository.findById(id);
            if (task != null) {
                log.info("Updating status of task {} to {}", id, statusStr);
                task.setStatus(TaskStatus.valueOf(statusStr));
                taskRepository.save(task);
            } else {
                log.warn("Task with ID {} not found for update", id);
            }
        } catch (IllegalArgumentException e) {
            log.error("Failed to update task status. Invalid ID '{}' or Status '{}'", taskId, statusStr, e);
        }
    }

    public void deleteTask(String taskId) {
        try {
            log.info("Deleting task with ID: {}", taskId);
            taskRepository.delete(UUID.fromString(taskId));
        } catch (IllegalArgumentException e) {
            log.error("Failed to delete task. Invalid UUID string: {}", taskId, e);
        }
    }
}