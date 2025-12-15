<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.codealchemists.tasklistapplication.model.User" %>
<html>
<head>
    <title>Users</title>
</head>
<body>
<h1>Users</h1>

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

<h2>Create user</h2>
<form method="post" action="users">
    <input type="hidden" name="action" value="create"/>
    <label>Username: <input type="text" name="username" required/></label><br/>
    <label>Password: <input type="password" name="password" required/></label><br/>
    <button type="submit">Create</button>
</form>

<h2>All users</h2>
<ul>
    <%
        List<User> users = (List<User>) request.getAttribute("users");
        if (users != null) {
            for (User u : users) {
    %>
    <li>
        <%= u.getUsername() %>
        <a href="tasks?userId=<%= u.getId() %>">Manage tasks</a>
    </li>
    <%
            }
        }
    %>
</ul>

</body>
</html>