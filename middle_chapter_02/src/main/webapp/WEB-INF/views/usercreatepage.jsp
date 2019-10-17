<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Create page</title>
</head>
<body>
<form action="${pageContext.servletContext.contextPath}/userscreate" method="post" enctype="multipart/form-data">
    ID: <input type="text" name="id"/></br>
    Name: <input type="text" name="name"/></br>
    Login: <input type="text" name="login"/></br>
    Password: <input type="text" name="password"/></br>
    Email: <input type="text" name="email"/></br>
    Role: <select name="rolename" required>
    <c:forEach items="${allRoles}" var="role">
        <option value="${role}"><c:out value="${role}"/></option>
        <c:out value="${role}"/>
    </c:forEach>
</select></br>
    Photo: <input type="file" name="file"></br>
    <input type="submit" value="add">
</form>
</body>
</html>
