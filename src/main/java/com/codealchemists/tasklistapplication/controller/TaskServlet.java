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

import java.io.IOException;

@WebServlet(name = "TaskServlet", urlPatterns = {"/tasks"})
public class TaskServlet extends HttpServlet {

    private final TaskService taskService = new TaskService();
    private final UserRepository userRepository = new UserRepository();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");

        if (user == null) {
            // 1. Check if "testuser" already exists in the Database
            user = userRepository.findByUsername("testuser");

            if (user == null) {
                // 2. If not, create AND SAVE the user to the Database
                // Pass null for ID so Hibernate generates it
                user = new User(null, "testuser", "password");
                userRepository.save(user);
            }

            // 3. Now this user object has a valid ID from the DB
            session.setAttribute("user", user);
        }

        req.setAttribute("tasks", taskService.getTasksForUser(user));
        req.getRequestDispatcher("/task-list.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        User user = (User) req.getSession().getAttribute("user");

        if ("create".equals(action)) {
            String title = req.getParameter("title");
            String shortDesc = req.getParameter("shortDescription");
            String longDesc = req.getParameter("longDescription");
            taskService.createTask(title, shortDesc, longDesc, user);
        } else if ("delete".equals(action)) {
            String taskId = req.getParameter("taskId");
            taskService.deleteTask(taskId);
        } else if ("updateStatus".equals(action)) {
            String taskId = req.getParameter("taskId");
            String status = req.getParameter("status");
            taskService.updateTaskStatus(taskId, status);
        }

        resp.sendRedirect("tasks");
    }
}