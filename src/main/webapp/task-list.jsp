<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.codealchemists.tasklistapplication.model.TaskStatus" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>

<html>
<head>
    <title>My Task List</title>
    <style>
        body { font-family: sans-serif; padding: 20px; }
        .task-card { border: 1px solid #ddd; padding: 15px; margin-bottom: 10px; border-radius: 5px; }
        .form-group { margin-bottom: 10px; }
        label { display: block; font-weight: bold; }
        input, textarea, select { width: 100%; padding: 5px; }
    </style>
</head>
<body>

<h1>Task List</h1>

<!-- Create Task Form -->
<div style="background: #f9f9f9; padding: 15px; margin-bottom: 20px;">
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
    <div class="task-card">
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
                <button type="submit" onclick="return confirm('Delete this task?')" style="background: #ffcccc; border: 1px solid red;">Delete</button>
            </form>
        </div>
    </div>
</c:forEach>

<p><a href="users">Go to Users</a></p>
</body>
</html>