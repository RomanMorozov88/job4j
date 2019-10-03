<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Create page</title>
</head>
<body>
<form action="${pageContext.servletContext.contextPath}/userscreate" method="post">
    ID: <input type="text" name="id"/></br>
    Name: <input type="text" name="name"/></br>
    Login: <input type="text" name="login"/></br>
    Email: <input type="text" name="email"/></br>
    <input type="submit" value="add">
</form>
</body>
</html>
