package com.codealchemists.tasklistapplication.controller;

import com.codealchemists.tasklistapplication.model.User;
import com.codealchemists.tasklistapplication.repository.UserRepository;
import com.codealchemists.tasklistapplication.service.TaskService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

@WebServlet(name = "TaskServlet", urlPatterns = {"/tasks"})
public class TaskServlet extends HttpServlet {

    private static final Logger log = LoggerFactory.getLogger(TaskServlet.class);

    private final TaskService taskService = new TaskService();
    private final UserRepository userRepository = new UserRepository();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info("Handling GET request for /tasks");
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");

        if (user == null) {
            log.info("No user in session. Checking/Creating 'testuser'.");
            // 1. Check if "testuser" already exists in the Database
            user = userRepository.findByUsername("testuser");

            if (user == null) {
                log.info("'testuser' not found. Creating new user.");
                // 2. If not, create AND SAVE the user to the Database
                // Pass null for ID so Hibernate generates it
                user = new User(null, "testuser", "password");
                userRepository.save(user);
            }

            // 3. Now this user object has a valid ID from the DB
            session.setAttribute("user", user);
        }

        log.debug("Retrieving tasks for user: {}", user.getUsername());
        req.setAttribute("tasks", taskService.getTasksForUser(user));
        req.getRequestDispatcher("/task-list.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        log.info("Handling POST request with action: {}", action);
        User user = (User) req.getSession().getAttribute("user");

        if ("create".equals(action)) {
            String title = req.getParameter("title");
            String shortDesc = req.getParameter("shortDescription");
            String longDesc = req.getParameter("longDescription");
            log.debug("Creating task: {}", title);
            taskService.createTask(title, shortDesc, longDesc, user);
            resp.sendRedirect("tasks");
        } else {
            if ("delete".equals(action)) {
                String taskId = req.getParameter("taskId");
                log.debug("Deleting task ID: {}", taskId);
                taskService.deleteTask(taskId);
            } else if ("updateStatus".equals(action)) {
                String taskId = req.getParameter("taskId");
                String status = req.getParameter("status");
                log.debug("Updating task ID: {} to status: {}", taskId, status);
                taskService.updateTaskStatus(taskId, status);
            }
            resp.sendRedirect("tasks");
        }

    }
}