<%@ include file="/init.jsp" %>
<%@ page import="com.example.todo.service.TodoLocalServiceUtil" %>
<%@ page import="com.example.todo.model.Todo" %>
<%@ page import="java.util.List" %>

<portlet:actionURL name="addTodo" var="addTodoActionURL" />

<div class="container-fluid p-4">
    <div class="card p-4 shadow-sm" style="max-width: 500px; margin: auto;">
        <h3 class="card-title text-primary mb-3">MySQL Enterprise Todo List</h3>

        <!-- Submission Form -->
        <form action="${addTodoActionURL}" method="post" class="mb-4">
            <div class="input-group">
                <input type="text" name="<portlet:namespace />todoText" class="form-control" placeholder="What needs to be done?" required />
                <div class="input-group-append">
                    <button type="submit" class="btn btn-success">Add Task</button>
                </div>
            </div>
        </form>

        <hr />

        <!-- Real-time Database Listing Block -->
        <h5 class="text-secondary mb-3">Active Items (Table: TODO_Todo)</h5>
        <ul class="list-group">
            <%
                List<Todo> todos = TodoLocalServiceUtil.getTodos(0, Integer.MAX_VALUE);
                if (todos.isEmpty()) {
            %>
                <li class="list-group-item text-muted text-center py-3">No tasks found in the database.</li>
            <%
                } else {
                    for (Todo t : todos) {
            %>
                <li class="list-group-item d-flex justify-content-between align-items-center">
                    <%= t.getText() %>
                    <span class="badge badge-warning">Pending</span>
                </li>
            <%
                    }
                }
            %>
        </ul>
    </div>
</div>
