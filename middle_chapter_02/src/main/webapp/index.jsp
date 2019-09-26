<%@ page import="ru.job4j.crudservletwebapp.models.User" %>
<%@ page import="java.util.List" %>
<%@ page import="ru.job4j.crudservletwebapp.logic.ValidateService" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Table</title>
</head>
<body>

<%List<User> list = ValidateService.getInstance().findAll();%>
<%if (list.size() > 0) {%>
All users:
<table border=1>
    <tr>
        <th>id</th>
        <th>name</th>
        <th>login</th>
        <th>email</th>
        <th>create date</th>
        <th colspan=2>actions</th>
    </tr>
        <%for (User u : list) {%>
    <tr>
        <td><%=u.getId()%>
        </td>
        <td><%=u.getName()%>
        </td>
        <td><%=u.getLogin()%>
        </td>
        <td><%=u.getEmail()%>
        </td>
        <td><%=u.getCreateDate()%>
        </td>
        <td>
            <form action="<%=request.getContextPath()%>/userupdatepage.jsp" method="get">
                <input type="hidden" name="id" value="<%=u.getId()%>"/>
                <input type="submit" value="update">
            </form>
        </td>
        <td>
            <form action="<%=request.getContextPath()%>/users" method="post">
                <input type="hidden" name="id" value="<%=u.getId()%>"/>
                <input type="submit" value="delete">
            </form>
        </td>
    </tr>
        <%}%>
        <%} else {%>
    <h1>List is empty.</h1>
        <%}%>
    <form action="<%=request.getContextPath()%>/usercreatepage.jsp" method="get">
        <input type="submit" value="add new user.">
    </form>
</body>
</html>
