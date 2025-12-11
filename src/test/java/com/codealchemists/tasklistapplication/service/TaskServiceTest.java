package com.codealchemists.tasklistapplication.service;

import com.codealchemists.tasklistapplication.model.Task;
import com.codealchemists.tasklistapplication.model.TaskStatus;
import com.codealchemists.tasklistapplication.model.User;
import com.codealchemists.tasklistapplication.repository.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    private TaskService taskService;

    private User testUser;

    @BeforeEach
    void setUp() {
        // Initialize the service with the mocked repository
        taskService = new TaskService(taskRepository);

        testUser = new User();
        testUser.setId(UUID.randomUUID());
        testUser.setUsername("unit_tester");
    }

    @Test
    void shouldCreateTaskSuccessfully() {
        String title = "New Unit Task";
        String shortDesc = "Short";
        String longDesc = "Long description";

        taskService.createTask(title, shortDesc, longDesc, testUser);

        // Capture the task object passed to the repository's save method
        ArgumentCaptor<Task> taskCaptor = ArgumentCaptor.forClass(Task.class);
        verify(taskRepository).save(taskCaptor.capture());

        Task capturedTask = taskCaptor.getValue();
        assertEquals(title, capturedTask.getTitle());
        assertEquals(shortDesc, capturedTask.getShortDescription());
        assertEquals(TaskStatus.TO_DO, capturedTask.getStatus());
        assertEquals(testUser, capturedTask.getUser());
    }

    @Test
    void shouldGetTasksForUser() {
        when(taskRepository.findAllByUser(testUser.getId())).thenReturn(List.of(new Task(), new Task()));

        List<Task> tasks = taskService.getTasksForUser(testUser);

        assertEquals(2, tasks.size());
        verify(taskRepository).findAllByUser(testUser.getId());
    }

    @Test
    void shouldUpdateTaskStatus() {
        UUID taskId = UUID.randomUUID();
        Task existingTask = new Task();
        existingTask.setId(taskId);
        existingTask.setStatus(TaskStatus.TO_DO);

        when(taskRepository.findById(taskId)).thenReturn(existingTask);

        taskService.updateTaskStatus(taskId.toString(), "DONE");

        assertEquals(TaskStatus.DONE, existingTask.getStatus());
        verify(taskRepository).save(existingTask);
    }

    @Test
    void shouldDeleteTask() {
        UUID taskId = UUID.randomUUID();

        taskService.deleteTask(taskId.toString());

        verify(taskRepository).delete(taskId);
    }
}