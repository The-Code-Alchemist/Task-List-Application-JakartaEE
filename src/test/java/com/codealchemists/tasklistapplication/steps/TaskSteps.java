package com.codealchemists.tasklistapplication.steps;

import com.codealchemists.tasklistapplication.model.Task;
import com.codealchemists.tasklistapplication.model.User;
import com.codealchemists.tasklistapplication.repository.TaskRepository;
import com.codealchemists.tasklistapplication.service.TaskService;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.stubbing.Answer;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doAnswer;

public class TaskSteps {

    private TaskService taskService;

    @Mock
    private TaskRepository taskRepository;

    private User currentUser;
    private final List<Task> mockDatabase = new ArrayList<>();

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        taskService = new TaskService(taskRepository);

        // Mock repository behavior to simulate DB with a list
        when(taskRepository.findAllByUser(any(UUID.class))).thenAnswer(invocation -> {
            UUID userId = invocation.getArgument(0);
            return mockDatabase.stream()
                    .filter(t -> t.getUser().getId().equals(userId))
                    .toList();
        });

        // Use doAnswer for void method 'save' to simulate persistence
        doAnswer(invocation -> {
            Task t = invocation.getArgument(0);
            if (t.getId() == null) {
                t.setId(UUID.randomUUID());
            }
            mockDatabase.add(t);
            return null;
        }).when(taskRepository).save(any(Task.class));
    }

    @Given("a user named {string} exists")
    public void a_user_named_exists(String username) {
        currentUser = new User();
        currentUser.setId(UUID.randomUUID());
        currentUser.setUsername(username);
    }

    @When("the user creates a task with title {string}")
    public void the_user_creates_a_task_with_title(String title) {
        taskService.createTask(title, "Description", "Long Description", currentUser);
    }

    @Then("the task list for {string} should contain {string}")
    public void the_task_list_for_should_contain(String username, String expectedTitle) {
        // In a real integration test, we might fetch from DB.
        // Here we rely on the service fetching from our mocked repository.
        List<Task> tasks = taskService.getTasksForUser(currentUser);

        boolean found = tasks.stream()
                .anyMatch(t -> t.getTitle().equals(expectedTitle));

        assertTrue(found, "Task with title " + expectedTitle + " not found!");
    }
}