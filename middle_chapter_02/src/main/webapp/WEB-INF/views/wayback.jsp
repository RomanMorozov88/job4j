<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Go back.</title>
</head>
<body>
<c:out value="${messageForBack}"></c:out>
<form action="${pageContext.servletContext.contextPath}/" method="get">
    <input type="submit" value="go back.">
</form>
</body>
</html>
