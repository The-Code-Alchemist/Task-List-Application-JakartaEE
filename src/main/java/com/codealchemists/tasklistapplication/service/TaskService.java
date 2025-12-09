package com.codealchemists.tasklistapplication.service;

import com.codealchemists.tasklistapplication.model.Task;
import com.codealchemists.tasklistapplication.model.TaskStatus;
import com.codealchemists.tasklistapplication.model.User;
import com.codealchemists.tasklistapplication.repository.TaskRepository;

import java.util.List;
import java.util.UUID;

public class TaskService {
    private final TaskRepository taskRepository;

    public TaskService() {
        this.taskRepository = new TaskRepository();
    }

    public List<Task> getTasksForUser(User user) {
        return taskRepository.findAllByUser(user.getId());
    }

    public void createTask(String title, String shortDesc, String longDesc, User user) {
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
                task.setStatus(TaskStatus.valueOf(statusStr));
                taskRepository.save(task);
            }
        } catch (IllegalArgumentException e) {
            // Log error or handle invalid UUID/Status
        }
    }

    public void deleteTask(String taskId) {
        try {
            taskRepository.delete(UUID.fromString(taskId));
        } catch (IllegalArgumentException e) {
            // Log error
        }
    }
}