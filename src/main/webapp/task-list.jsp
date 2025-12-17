<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.codealchemists.tasklistapplication.model.TaskStatus" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>

<html>
<head>
    <title>My Task List</title>
    <link rel="stylesheet" href="assets/css/app.css">
</head>
<body>

<h1>Task List</h1>

<div class="nav">
    <a href="users">Users</a>
</div>

<!-- Create Task Form -->
<div class="panel">
    <h3>Add New Task</h3>
    <form action="tasks" method="post">
        <input type="hidden" name="action" value="create">
        <div class="form-group">
            <label>Title:</label>
            <input type="text" name="title" required>
        </div>
        <div class="form-group">
            <label>Short Description:</label>
            <input type="text" name="shortDescription" required>
        </div>
        <div class="form-group">
            <label>Long Description:</label>
            <textarea name="longDescription"></textarea>
        </div>
        <button type="submit">Add Task</button>
    </form>
</div>

<!-- Task List -->
<c:forEach items="${tasks}" var="task">
    <div class="card">
        <h3>${task.title} <span class="status-badge">${task.status.displayText}</span></h3>
        <p><strong>Summary:</strong> ${task.shortDescription}</p>
        <p>${task.longDescription}</p>

        <div style="margin-top: 10px;">
            <!-- Update Status Form -->
            <form action="tasks" method="post" style="display:inline;">
                <input type="hidden" name="action" value="updateStatus">
                <input type="hidden" name="taskId" value="${task.id}">
                <select name="status" onchange="this.form.submit()">
                    <c:forEach items="<%= TaskStatus.values() %>" var="status">
                        <option value="${status}" ${task.status == status ? 'selected' : ''}>${status.displayText}</option>
                    </c:forEach>
                </select>
            </form>

            <!-- Delete Form -->
            <form action="tasks" method="post" style="display:inline; float:right;">
                <input type="hidden" name="action" value="delete">
                <input type="hidden" name="taskId" value="${task.id}">
                <button type="submit" onclick="return confirm('Delete this task?')" class="btn-danger">Delete</button>
            </form>
        </div>
    </div>
</c:forEach>

</body>
</html>