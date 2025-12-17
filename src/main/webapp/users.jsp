<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.codealchemists.tasklistapplication.model.User" %>
<html>
<head>
    <title>Users</title>
    <link rel="stylesheet" href="assets/css/app.css">
</head>
<body>
<h1>Users</h1>

<div class="nav">
    <a href="tasks">Tasks</a>
</div>

<%
    String error = (String) request.getAttribute("error");
    if (error != null) {
%>
<div style="color: #b00020; margin: 12px 0;">
    <strong>Error:</strong> <%= error %>
</div>
<%
    }
%>

<!-- Create User Form -->
<div class="panel">
    <h3>Create user</h3>
    <form method="post" action="users">
        <input type="hidden" name="action" value="create"/>

        <div class="form-group">
            <label>Username:</label>
            <input type="text" name="username" required/>
        </div>

        <div class="form-group">
            <label>Password:</label>
            <input type="password" name="password" required/>
        </div>

        <button type="submit">Create</button>
    </form>
</div>

<h3>All users</h3>

<%
    List<User> users = (List<User>) request.getAttribute("users");
    if (users != null) {
        for (User u : users) {
%>
<div class="card">
    <h3><%= u.getUsername() %></h3>
    <p style="margin: 0;">
        <a href="tasks?userId=<%= u.getId() %>">Manage tasks</a>
    </p>
</div>
<%
        }
    }
%>

</body>
</html>