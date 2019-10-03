<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Update Page</title>
</head>
<body>
<form action="${pageContext.servletContext.contextPath}/usersupdate" method="post">
    <input type="hidden" name="id" value="${userForUpdate.id}"/><br/>
    ID: <c:out value="${userForUpdate.id}"></c:out><br/>
    Name: <input type="text" name="name" value="${userForUpdate.name}"/><br/>
    Login: <input type="text" name="login" value="${userForUpdate.login}"/><br/>
    Email: <input type="text" name="email" value="${userForUpdate.email}"/><br/>
    Create date: <c:out value="${userForUpdate.createDate}"></c:out><br/>
    <input type="submit" value="update">
</form>
</body>
</html>
