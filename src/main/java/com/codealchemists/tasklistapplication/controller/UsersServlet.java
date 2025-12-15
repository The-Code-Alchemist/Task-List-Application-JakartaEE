package com.codealchemists.tasklistapplication.controller;

import com.codealchemists.tasklistapplication.model.User;
import com.codealchemists.tasklistapplication.repository.UserRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "UsersServlet", urlPatterns = {"/users"})
public class UsersServlet extends HttpServlet {

    private final UserRepository userRepository = new UserRepository();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        render(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String action = req.getParameter("action");
        if (!"create".equals(action)) {
            resp.sendRedirect("users");
            return;
        }

        String username = trimToNull(req.getParameter("username"));
        String password = trimToNull(req.getParameter("password"));

        if (username == null || password == null) {
            req.setAttribute("error", "Username and password are required.");
            render(req, resp);
            return;
        }

        User existing = userRepository.findByUsername(username);
        if (existing != null) {
            req.setAttribute("error", "Username already exists. Please choose another.");
            render(req, resp);
            return;
        }

        User user = new User(null, username, password);
        userRepository.save(user);

        resp.sendRedirect("users");
    }

    private void render(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<User> users = userRepository.findAll();
        req.setAttribute("users", users);
        req.getRequestDispatcher("/users.jsp").forward(req, resp);
    }

    private static String trimToNull(String s) {
        if (s == null) return null;
        String t = s.trim();
        return t.isEmpty() ? null : t;
    }
}