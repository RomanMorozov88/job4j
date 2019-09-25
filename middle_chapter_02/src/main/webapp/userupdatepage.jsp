<%@ page import="ru.job4j.crudservletwebapp.models.User" %>
<%@ page import="ru.job4j.crudservletwebapp.persistent.MemoryStore" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Update Page</title>
</head>
<body>
<%Integer id = Integer.parseInt(request.getParameter("id"));%>
<%User bufferUser = MemoryStore.getInstance().findById(id);%>
<form action="<%=request.getContextPath()%>/usersupdate" method="post">
    <input type="hidden" name="id" value="<%=bufferUser.getId()%>"/><br/>
    ID: <%=bufferUser.getId()%><br/>
    Name: <input type="text" name="name" value="<%=bufferUser.getName()%>"/><br/>
    Login: <input type="text" name="login" value="<%=bufferUser.getLogin()%>"/><br/>
    Email: <input type="text" name="email" value="<%=bufferUser.getEmail()%>"/><br/>
    Create date: <%=bufferUser.getCreateDate()%><br/>
    <input type="submit" value="update">
</form>
</body>
</html>
